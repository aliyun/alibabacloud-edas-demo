package main

import (
	"github.com/alibaba/schedulerx-worker-go"
)

func main() {
	// This is just an example, the real configuration needs to be obtained from the platform
	cfg := &schedulerx.Config{
		Endpoint:  "acm.aliyun.com",
		Namespace: "433d8b23-06e9-xxx-xxx-90d4d1b9a4af",
		GroupId:   "xueren_sub",
		AppKey:    "xxxxxx",
	}
	client, err := schedulerx.GetClient(cfg)
	if err != nil {
		panic(err)
	}
	task := &HelloWorld{}

	// The name HelloWorld registered here must be consistent with the configured on the platform
	client.RegisterTask("HelloWorld", task)
	select {}
}
