package main

import (
	"encoding/json"
	"errors"
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
		num = 10
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
			var str = fmt.Sprintf("id_%d", i)
			messageList = append(messageList, str)
		}
		fmt.Println(messageList)
		return mr.Map(jobCtx, messageList, "Level1Dispatch")
	} else if taskName == "Level1Dispatch" {
		var task []byte = jobCtx.Task()
		var str string
		err = json.Unmarshal(task, &str)
		fmt.Printf("str=%s\n", str)
		time.Sleep(100 * time.Millisecond)
		fmt.Println("Finish Process...")
		if str == "id_5" {
			return processor.NewProcessResult(
				processor.WithFailed(),
				processor.WithResult(str),
			), errors.New("test")
		}
		return processor.NewProcessResult(
			processor.WithSucceed(),
			processor.WithResult(str),
		), nil
	}
	return processor.NewProcessResult(processor.WithFailed()), nil
}
