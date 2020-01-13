package swjtu.zkd.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import swjtu.zkd.miaosha.domain.MiaoshaUser;

@Mapper
public interface MiaoshaUserDAO {

    String TABLE_NAME = "miaosha_user";
    String ALL_FIELDS = " id,nickname,password,salt,head,register_date,last_login_date,login_count ";

    @Select({"select", ALL_FIELDS, "from ", TABLE_NAME, " where id = #{id}"})
    MiaoshaUser getById(@Param("id") long id);
}
