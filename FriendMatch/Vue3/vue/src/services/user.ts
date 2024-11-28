import myAxios from '../plugins/myAxios';
import { setCurrentUserState } from '../states/user';
export const getCurrentUser = async () => {
    //不存在规则从远程获取
    const res = await myAxios.get('/user/current');
    if(res.data.code === 0){
        setCurrentUserState(res.data.data);
        return res.data.data;
    }else{
        return null;
    }
}