import Index from "../pages/Index.vue"
import LoginPage from "../pages/LoginPage.vue"
import SearchPage from "../pages/SearchPage.vue"
import SearchResultPage from "../pages/SearchResultPage.vue"
import Team from "../pages/Team.vue"
import TeamAddPage from "../pages/TeamAddPage.vue"
import TeamUpdatePage from "../pages/TeamUpdatePage.vue"
import User from "../pages/User.vue"
import UserEditPage from "../pages/UserEditPage.vue"
// import TeamAddPage from "../pages/TeamAddPage.vue"
import ForgetPasswordVue from "../pages/ForgetPassword.vue"
import GenderSelect from "../pages/GenderSelect.vue"
import Register from "../pages/Register.vue"
import TagVue from "../pages/Tag.vue"
import TeamCreatePage from "../pages/TeamCreatePage.vue"
import TeamJoinPage from "../pages/TeamJoinPage.vue"
import UserUpdate from "../pages/UserUpdate.vue"
import UserUpload from "../pages/UserUpload.vue"
const routes = [
{path: "/", component: Index},
{path:"/team",title:'找队伍', component: Team},
{path:"/user",title:'个人中心', component: User},
{path:"/search",title:'搜索', component: SearchPage},
{path:"/user/edit",title:'编辑个人信息', component: UserEditPage},
{path:"/user/list",title:'用户列表',  component: SearchResultPage},
{path:"/user/login",title:'登录', component: LoginPage},
{path:"/team/add", title:'创建队伍',component: TeamAddPage},
{path:"/team/update",title:'修改队伍信息', component: TeamUpdatePage},
{path:"/team/join",title:'加入的队伍', component: TeamJoinPage},
{path:"/team/create",title:'创建的队伍', component: TeamCreatePage},
{path:"/user/upload",title:'上传头像', component: UserUpload},
{path:"/user/update",title:'修改个人信息', component: UserUpdate},
{path:"/gender/select",title:'性别选择', component: GenderSelect},
{path:"/user/tag",title:'标签管理', component: TagVue},
{path:"/user/register",title:'账号注册', component: Register},
{path:"/user/forget",title:'忘记密码', component: ForgetPasswordVue},
]

export default routes