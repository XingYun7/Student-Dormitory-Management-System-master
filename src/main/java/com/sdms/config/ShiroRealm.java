package com.sdms.config;

import com.sdms.entity.User;
import com.sdms.service.UserService;
import lombok.val;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    public String getName() {
        return "Customized ShiroRealm";
    }

    //认证(登录的逻辑)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        val up = (UsernamePasswordToken) token;
        val username = up.getUsername();
        val user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UnknownAccountException(); // 根据用户名查询不到用户,交给shiro捕获用户不存在的异常
        }
        // 将 username 作为加密的盐值
        val salt = ByteSource.Util.bytes(user.getUsername());
        // 将 user (作为 principal)、数据库中加密后的密码交给Shiro框架完成认证
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        val user = (User) principalCollection.getPrimaryPrincipal();
        val info = new SimpleAuthorizationInfo();
        val role = user.getRole();
        info.addRole(role.getName());
        for (val permission : role.getPermissions()) {
            info.addStringPermission(permission.getCode());
        }
        return info;
    }

}