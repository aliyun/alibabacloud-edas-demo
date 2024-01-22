package main

import (
	"fmt"
	"github.com/alibaba/schedulerx-worker-go/processor"
	"github.com/alibaba/schedulerx-worker-go/processor/jobcontext"
	"time"
)

var _ processor.Processor = &HelloWorld{}

type HelloWorld struct{}

func (h *HelloWorld) Process(ctx *jobcontext.JobContext) (*processor.ProcessResult, error) {
	fmt.Println("[Process] Start process my task: Hello world!")
	// mock execute task
	time.Sleep(3 * time.Second)
	ret := new(processor.ProcessResult)
	ret.SetStatus(processor.InstanceStatusSucceed)
	fmt.Println("[Process] End process my task: Hello world!")
	return ret, nil
}
