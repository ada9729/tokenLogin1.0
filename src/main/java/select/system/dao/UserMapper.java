package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.User;
import select.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @author yeyuting
 * @create 2021/1/25
 */
@Repository
@Mapper
public interface UserMapper {

    public User selectByName(String username)  ;

    public User selectById (int id ) ;

    public List<User> selectAll() ;

    public boolean insertOne(User user) ;

    public boolean insertMany(List<User> userList) ;

    public boolean updateOne(User user) ;

    public boolean deleteById(int id) ;

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) ;

    public List<User> selectByMap(Map<String, Object> map) ;

    public List<User> SelectByPageBean(PageBean pageBean) ;

    public List<User> selectByLike(Map<String , Object> map) ;

    public User selectByUserName(String username) ;

    //转出更新
    public boolean updateAccountOut (Double accountMoney , String userName) ;
    //转入更新
    public boolean updateAccountIn (Double accountMoney , String userName) ;

    //转出记录插入
    public boolean accountOutInsert(String userName ,Double account , Double accountMoney , String targetAccount , String accountType) ;

    //转入记录插入
    public boolean accountInInsert(String userName ,Double account , Double accountMoney , String targetAccount , String accountType) ;


}
