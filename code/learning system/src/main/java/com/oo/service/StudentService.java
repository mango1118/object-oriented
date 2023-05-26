package com.oo.service;

import com.oo.domain.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:学生服务类
 * @author: mango
 * @date: 2023/5/26 15:34
 */
@Transactional
public interface StudentService {

    /**
     * 保存student
     *
     * @param student
     * @return boolean
     */
    public boolean save(Student student);

    /**
     * 更新student
     *
     * @param student
     * @return boolean
     */
    public boolean update(Student student);

    /**
     * 删除student
     *
     * @param id
     * @return boolean
     */
    public boolean deleteById(Integer id);

    /**
     * 通过id返回student
     *
     * @param id
     * @return student
     */
    public Student selectById(Integer id);

    /**
     * 返回全部student
     *
     * @return List<Admin>
     */
    public List<Student> getAll();

    /**
     * 分页模糊查询
     *
     * @param current
     * @param size
     * @param name
     * @return Map
     */
    public Map<String, Object> selectLike(Integer current, Integer size, String id, String name);

}
