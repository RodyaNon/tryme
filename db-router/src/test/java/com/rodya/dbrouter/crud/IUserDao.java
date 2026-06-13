package com.rodya.dbrouter.crud;

import com.rodya.dbrouter.annotation.DBRouter;
import com.rodya.dbrouter.annotation.DBRouterStrategy;
import org.apache.ibatis.annotations.*;

@Mapper
@DBRouterStrategy(true)
public interface IUserDao {

    @DBRouter
    @Select("select * from user where user_id = #{userId}")
    User queryByUserId(String userId);

    @DBRouter
    @Insert("insert into user(user_id, user_name) values(#{userId}, #{userName})")
    void saveUser(User user);

    @DBRouter
    @Update("update user set user_name = #{userName} where user_id = #{userId}")
    void updateUser(User user);

    @DBRouter
    @Delete("delete from user where user_id = #{userId}")
    void deleteUser(String userId);

}
