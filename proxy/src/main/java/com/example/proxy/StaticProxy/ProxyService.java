package com.example.proxy.StaticProxy;

/**
 * @ClassName ProxyService
 * @Description TODO
 * @Author user
 * @Date 2019/12/10
 * @Version 1.0
 */
public class ProxyService implements IUserService {
    //静态代理实现比较简单，但是在实际项目中我们需要为每个类都写一个代理类，
    // 需要写很多重复冗余的代码，不利于代码的解耦与扩展。
    private UserService mUserService;

    public ProxyService(UserService mUserService) {
        this.mUserService = mUserService;
    }

    @Override
    public void addUser() {
        mUserService.addUser();
    }

    @Override
    public void deleteUser() {
        mUserService.deleteUser();
    }
}
