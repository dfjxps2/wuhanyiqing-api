package io.dfjinxin.modules.wash.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.dto.ProgramForm;
import io.dfjinxin.modules.wash.service.WashRuleProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wash/rule/program")
public class WashRuleProgramController extends AbstractController {

    @PostMapping("/query")
    public R query(@RequestBody ProgramForm programForm){
        programForm.setTnmtid(this.getTenantId());
        return R.ok().put("page", washRuleProgramService.query(programForm));
    }

    @Autowired
    private WashRuleProgramService washRuleProgramService;
}
