package main

import (
	"github.com/alibaba/schedulerx-worker-go"
	"github.com/alibaba/schedulerx-worker-go/processor/mapjob"
)

func main() {
	// This is just an example, the real configuration needs to be obtained from the platform
	cfg := &schedulerx.Config{
		DomainName: "schedulerx2.taobao.net",
		Namespace:  "system_namespace",
		GroupId:    "dts-demo",
		AppKey:     "0H8ww7zs+17gwEpashSDpg==",
	}
	client, err := schedulerx.GetClient(cfg)
	if err != nil {
		panic(err)
	}
	task1 := &HelloWorld{}
	task2 := &TestBroadcast{}
	task3 := &TestMapJob{
		mapjob.NewMapJobProcessor(),
	}
	task4 := &TestMapReduceJob{
		mapjob.NewMapReduceJobProcessor(),
	}

	// The name HelloWorld registered here must be consistent with the configured on the platform
	client.RegisterTask("HelloWorld", task1)
	client.RegisterTask("TestBroadcast", task2)
	client.RegisterTask("TestMapJob", task3)
	client.RegisterTask("TestMapReduceJob", task4)
	select {}
}
