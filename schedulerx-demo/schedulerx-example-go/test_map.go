package main

import (
	"fmt"
	"github.com/alibaba/schedulerx-worker-go/processor"
	"github.com/alibaba/schedulerx-worker-go/processor/jobcontext"
	"github.com/alibaba/schedulerx-worker-go/processor/mapjob"
	"strconv"
	"time"
)

type TestMapJob struct {
	*mapjob.MapJobProcessor
}

func (mr *TestMapJob) Kill(jobCtx *jobcontext.JobContext) error {
	//TODO implement me
	panic("implement me")
}

// Process the MapReduce model is used to distributed scan orders for timeout confirmation
func (mr *TestMapJob) Process(jobCtx *jobcontext.JobContext) (*processor.ProcessResult, error) {
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
		fmt.Println("start root task")
		var messageList []interface{}
		for i := 1; i <= num; i++ {
			messageList = append(messageList, fmt.Sprintf("id_%d", i))
			//			orderInfos = append(orderInfos, NewOrderInfo(fmt.Sprintf("id_%d", i), i))
		}
		return mr.Map(jobCtx, messageList, "Level1Dispatch")
	} else if taskName == "Level1Dispatch" {
		var str []byte = jobCtx.Task()
		var s = string(str)
		fmt.Printf("str=%s\n", s)
		time.Sleep(100 * time.Millisecond)
		fmt.Println("Finish Process...")
		return processor.NewProcessResult(
			processor.WithSucceed(),
			processor.WithResult(s),
		), nil
	}
	return processor.NewProcessResult(processor.WithFailed()), nil
}
