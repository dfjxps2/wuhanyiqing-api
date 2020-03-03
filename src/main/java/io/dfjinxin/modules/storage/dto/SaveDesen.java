package io.dfjinxin.modules.storage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SaveDesen {

    private Integer jobId;//页面无需传参
    private String czJob;//是否需要删除周期任务，01需要删除周期任务, 02立即执行，03周期执行

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date jobStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date jobFinishTime;

    private Integer tbId;

    private int jobType;//作业类型

    private List<DesenFld> desenFlds;
}
