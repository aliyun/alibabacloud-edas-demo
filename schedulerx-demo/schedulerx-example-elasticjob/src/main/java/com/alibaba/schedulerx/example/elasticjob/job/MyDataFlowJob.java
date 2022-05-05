package com.alibaba.schedulerx.example.elasticjob.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.stereotype.Component;

import com.alibaba.schedulerx.example.elasticjob.domain.Foo;
import com.alibaba.schedulerx.example.elasticjob.domain.FooRepository;

@Component
public class MyDataFlowJob implements DataflowJob<Foo> {

    @Resource
    private FooRepository fooRepository;
    
    @Override
    public List<Foo> fetchData(final ShardingContext shardingContext) {
        System.out.println("start to featchData, shading=" +shardingContext.getShardingParameter());
        return fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
    }
    
    @Override
    public void processData(final ShardingContext shardingContext, final List<Foo> data) {
        for (Foo each : data) {
            System.out.println("processData, " + each);
            fooRepository.setCompleted(each.getId());
        }
    }

}
