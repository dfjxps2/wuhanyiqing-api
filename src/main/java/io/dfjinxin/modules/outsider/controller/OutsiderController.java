package io.dfjinxin.modules.outsider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.outsider.entity.DetainedPersonInfoEntity;
import io.dfjinxin.modules.outsider.service.DetainedPersonInfoService;


/**
 * 滞汉外地人明细 controller
 * @author zhujiazhou
 *
 */
@RestController
@RequestMapping("/outsider")
public class OutsiderController {

    @Autowired
    private DetainedPersonInfoService personService;
    
	/**
	 * 保存用户
 */
	@PostMapping("/save")
	public R save(@RequestBody DetainedPersonInfoEntity person){
		personService.save(person);
	return R.ok();
}

/**
 * 修改用户
	 */
@PostMapping("/update")
public R update(@RequestBody  DetainedPersonInfoEntity person){
	personService.saveOrUpdate(person);
	return R.ok();
	}
    
}
