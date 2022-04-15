/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class ConnServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private DruidDataSource dataSource1;
    private DruidDataSource dataSource2;
    private WebApplicationContext applicationContext;
    

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletRequest request = req;
        String index = req.getParameter("ds");
        DruidDataSource dataSource = null;
        if("2".equals(index)) {
            dataSource = dataSource2; 
        }else {
            dataSource = dataSource1;
        }
        DruidPooledConnection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("Select COUNT(*) As X From INFORMATION_SCHEMA.SYSTEM_USERS Where 1=0");
            String sleep = request.getParameter("sleep");
            if(sleep != null){
                Thread.sleep(Long.valueOf(sleep));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        PrintWriter writer = resp.getWriter();
        writer.write("active count:" + dataSource.getActiveCount());
        writer.write(",stat data:" + dataSource.getStatData());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        dataSource1 = (DruidDataSource) applicationContext.getBean("dataSource1");
        dataSource2 = (DruidDataSource) applicationContext.getBean("dataSource2");
    }
    
    
    
}
