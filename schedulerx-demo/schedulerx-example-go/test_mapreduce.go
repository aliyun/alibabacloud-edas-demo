package main

import (
	"encoding/json"
	"fmt"
	"github.com/alibaba/schedulerx-worker-go/processor"
	"github.com/alibaba/schedulerx-worker-go/processor/jobcontext"
	"github.com/alibaba/schedulerx-worker-go/processor/mapjob"
	"github.com/alibaba/schedulerx-worker-go/processor/taskstatus"
	"strconv"
	"time"
)

type OrderInfo struct {
	Id    string `json:"id"`
	Value int    `json:"value"`
}

func NewOrderInfo(id string, value int) *OrderInfo {
	return &OrderInfo{Id: id, Value: value}
}

type TestMapReduceJob struct {
	*mapjob.MapReduceJobProcessor
}

func (mr *TestMapReduceJob) Kill(jobCtx *jobcontext.JobContext) error {
	//TODO implement me
	panic("implement me")
}

// Process the MapReduce model is used to distributed scan orders for timeout confirmation
func (mr *TestMapReduceJob) Process(jobCtx *jobcontext.JobContext) (*processor.ProcessResult, error) {
	var (
		num = 1000
		err error
	)
	taskName := jobCtx.TaskName()

	if jobCtx.JobParameters() != "" {
		num, err = strconv.Atoi(jobCtx.JobParameters())
		if err != nil {
			return nil, err
		}
	}

	if mr.IsRootTask(jobCtx) {
		fmt.Println("start root task, taskId=%d", jobCtx.TaskId())
		var orderInfos []interface{}
		for i := 1; i <= num; i++ {
			orderInfos = append(orderInfos, NewOrderInfo(fmt.Sprintf("id_%d", i), i))
		}
		return mr.Map(jobCtx, orderInfos, "OrderInfo")
	} else if taskName == "OrderInfo" {
		orderInfo := new(OrderInfo)
		if err := json.Unmarshal(jobCtx.Task(), orderInfo); err != nil {
			fmt.Printf("task is not OrderInfo, task=%+v\n", jobCtx.Task())
		}
		fmt.Printf("taskId=%d, orderInfo=%+v\n", jobCtx.TaskId(), orderInfo)
		time.Sleep(1 * time.Millisecond)
		return processor.NewProcessResult(
			processor.WithSucceed(),
			processor.WithResult(strconv.Itoa(orderInfo.Value)),
		), nil
	}
	return processor.NewProcessResult(processor.WithFailed()), nil
}

func (mr *TestMapReduceJob) Reduce(jobCtx *jobcontext.JobContext) (*processor.ProcessResult, error) {
	allTaskResults := jobCtx.TaskResults()
	allTaskStatuses := jobCtx.TaskStatuses()
	count := 0
	fmt.Printf("reduce: all task count=%d\n", len(allTaskResults))
	for key, val := range allTaskResults {
		if key == 0 {
			continue
		}
		if allTaskStatuses[key] == taskstatus.TaskStatusSucceed {
			num, err := strconv.Atoi(val)
			if err != nil {
				return nil, err
			}
			count += num
		}
	}
	fmt.Printf("reduce: succeed task count=%d\n", count)
	return processor.NewProcessResult(
		processor.WithSucceed(),
		processor.WithResult(strconv.Itoa(count)),
	), nil
}
