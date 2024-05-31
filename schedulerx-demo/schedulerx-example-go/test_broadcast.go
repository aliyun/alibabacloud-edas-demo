package main

import (
	"fmt"
	"github.com/alibaba/schedulerx-worker-go/processor"
	"github.com/alibaba/schedulerx-worker-go/processor/jobcontext"
	"github.com/alibaba/schedulerx-worker-go/processor/taskstatus"
	"math/rand"
	"strconv"
)

type TestBroadcast struct{}

// Process all machines would execute it.
func (t TestBroadcast) Process(ctx *jobcontext.JobContext) (*processor.ProcessResult, error) {
	value := rand.Intn(10)
	fmt.Printf("Total sharding num=%d, sharding=%d, value=%d\n", ctx.ShardingNum(), ctx.ShardingId(), value)
	ret := new(processor.ProcessResult)
	ret.SetSucceed()
	ret.SetResult(strconv.Itoa(value))
	return ret, nil
}

// PreProcess only one machine will execute it.
func (t TestBroadcast) PreProcess(ctx *jobcontext.JobContext) error {
	fmt.Println("TestBroadcastJob PreProcess")
	return nil
}

// PostProcess only one machine will execute it.
func (t TestBroadcast) PostProcess(ctx *jobcontext.JobContext) (*processor.ProcessResult, error) {
	fmt.Println("TestBroadcastJob PostProcess")
	allTaskResults := ctx.TaskResults()
	allTaskStatuses := ctx.TaskStatuses()
	num := 0

	for key, val := range allTaskResults {
		fmt.Printf("%v:%v\n", key, val)
		if allTaskStatuses[key] == taskstatus.TaskStatusSucceed {
			valInt, _ := strconv.Atoi(val)
			num += valInt
		}
	}

	fmt.Printf("TestBroadcastJob PostProcess(), num=%d\n", num)
	ret := new(processor.ProcessResult)
	ret.SetSucceed()
	ret.SetResult(strconv.Itoa(num))
	return ret, nil
}
