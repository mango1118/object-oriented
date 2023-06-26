package com.oo.service;


import com.oo.domain.Admin;
import com.oo.domain.Student;
import org.springframework.transaction.annotation.Transactional;


import com.oo.domain.Teacher;

import java.util.List;
import java.util.Map;

@Transactional
public interface AdminService {
    /**
     * @description: 增加教师
     */
    public boolean addTeacher(Teacher teacher);

    /**
     * @description: 根据id删除教师
     */
    public boolean deleteTeacherById(Integer id);

    /**
     * @description: 修改教师信息
     */

    public boolean modifyTeacher(Teacher teacher);

    /**
     * @description: 根据id查找教师信息
     */
    public Teacher getTeacherById(Integer id);

    /**
     * @description: 查找所有教师信息
     */
    public List<Teacher> getAll();

    /**
     * 分页模糊查询,以教师账户和教师名字为条件
     * @return Map
     */
    public Map<String, Object> selectLike(Integer current, Integer size, String account, String name);


    /**
     * @description: 修改管理员信息
     */

    public boolean modifyAdmin(Admin admin);


    /**
     * @description: 获取管理员信息
     */
    public Admin selectById(Integer id);
}
