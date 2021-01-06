# MyQQChat
一个仿造QQ的聊天软件，目前已经实现了客户端和服务器的大体框架和基础功能，可扩展性良好，可在这之上进行二次开发。
1.view
    LoginView 用户登录界面
        void login()
            用户登录，首先提取出用户输入的用户名与密码，对输入的信息进行合法性检查
	        然后与sso服务器建立套接字，构造实体对象，通过user实体、json字符串、请求方法、登录方法构建请求对象，通过SocketUtils发送登录请求到sso服务器
	        接受来自sso服务器的响应，提取响应消息的状态码，判断是否登录成功
	        如果返回的消息不为空且状态码为200，则登录成功，并通过sso对象返回的信息构建完整的user实体对象
	        登录成功，析构此页面，根据user实体和socket对象构造好友列表界面，并且构造好友列表信息
    RegisterView 用户注册界面
        负责向Server发送注册请求
    FriendListView 好友列表界面
        FriendListView(User user,Socket socket )
            构造方法
            初始化好友列表界面，复用客户端与sso服务器建立的套接字，初始化此类的user实体对象
	        从服务层FirendService向server发出请求查询此user的好友信息
	        如果服务器返回的消息为空，则网络连接超时，产生警示信息
	        根据服务器返回的消息判断状态，如果状态码为200，则成功获取到好友信息，通过返回的信息构造好友列表，否则产生警示信息
        void createFrame()
            通过好友信息集合firendsList构造好友列表清单，并且初始化该用户的好友信息friendsMap，name-record对，即可通过好友姓名查找各种好友信息
	        遍历集合，判断好友是否在线，如果在线则构造好友条目，并且添加按钮和监听器，可以点击此按钮开始与好友进行聊天对话
	        添加对此页面的监听，如果关闭则进行资源释放，当聊天页面被关闭时需要将socket和线程thread关闭否者会报错
	        启动监听好友上线、下线的线程
        talkButton(JLabel jLabel,Record r )
            为好友条目添加监听器，获取鼠标点击的好友条目，根据此条目在friendsMap中找到对应的record信息
	        从record中取出信息，通过反射构造user对象，与消息 中转服务器 建立套接字，并发送通话请求，请求方法为talklink
	        如果用户点击好友条目，则打开和好友的对话框
    TalkView 对话框界面
        TalkView(User u, User firends,Socket socket)
            使用对话的两个用户对象、和消息中转服务器建立的套接字初始化该对象
        void createFrame()
            构造界面，启动对话线程
        void sendMSG()
            发送用户输入的消息给消息服务器
        void windowsClose(TalkThread talkThead)
            添加对聊天页面的监听，当聊天页面被关闭时，妥善地释放资源，向消息中转服务器发送关闭对话的请求，关闭当前对话线程
2.service
    FirendService
    UserService 继承自 SocketUtils 
    主要负责向服务器发送请求，和读取服务器的响应消息
3.utils
    RefreshThread
        当此线程处于运行的时候，不断轮询是否有来自服务器的消息，包括好友上线下线发送消息通知
        如果接收到好友上线的消息，则将好友的条目设置为彩色
        如果接收到好友下线的消息，则将好友的条目设置为灰色
        如果接收到好友发送来的信息的消息，则将好友的条目设置为红色
    TalkThread
        每个talkview都有一个此线程
        在打开聊天窗口时，通过轮询来即时接收来自于消息中转服务器的消息，并将其显示在对话框中
        如果接收到的请求的方法为receiveMsg，则为建立之初，消息服务器向用户返回缓存的消息
        如果接收到的请求的方法为talk，则为用户发送的实时消息

二.        QQSso 单点登录服务器     端口号：80
1.SsoApplication
    初始化sso服务器监听端口号，一旦用用户与服务器建立套接字，调用分派方法处理用户的请求
2.SsoService
    void dispathSevlet(Socket socket)
        负责对用户的请求进行分派
    Response login(Request request,Socket socket)
        处理用户登录请求，判断用户输入的信息是否正确，包括账号是否存在，密码是否正确
	    用户成功登录后，sso服务器会将记录用户id - 套接字映射，以便在用户之间转发消息,还会记录id-user映射
        同时通过反射构造user实体对象发送给客户端（包括用户在数据库中的全部信息），更新数据库中用户登录的标志位
	    sso服务器帮助用户广播自己登录成功的消息，通过遍历已经与sso服务器建立套接字连接的映射集来广播消息
        如果用户id-密码错误，则发送登录失败响应给用户
    Response loginCheck(Request request)
        直接在缓存中查询是否存在用户登录的记录，从而判断用户是否在线，并封装对应相应发送给用户，如果用户在线
        还会将用户与sso服务器建立的套接字封装入响应
    void logOut(Request request)
        用户下线功能，广播给好友下线的消息
	    并对数据库做出相应的修改，修改是否在线标识位为0  
        异常缓存中的用户相关的登录信息键值对  

三.     QQIMServer 消息中转服务器   端口号：9998
1.entity
    TalkCache
2.service
    TalkThread
    每当有一个用户与sso服务器建立套接字，sso服务器建立一个线程，用于用户的消息中转
        TalkThread(Socket s, Map<String,List<Message>> Cache)
            为每个与消息中转服务器建立连接的用户建立一条用于通信的线程，线程建立需要完成的初始化工作有
            向threadmap中加入 会话-线程 的映射、读取套接字中用户发送的建立会话（talklink）的请求，读取cache中缓存的消息，并且发送回用户、同时移除cache中已发送的缓存消息
        void run()
            线程在执行的过程当中，会不断地尝试读取套接字中内容，根据不同的请求方法采取不同的措施
            如果请求方法为talk，则调用talkdispose处理请求
            如果请求方法为talkviewclose，则设置线程中断标志位为真，并且移除集合中 会话-线程 的映射，之后此线程会结束
        void talkDispose(Request request)
            负责消息中转发送给朋友，首先获取用户Id和朋友id，然后根据从集合中获取朋友的socket
            如果获取到了，说明朋友已经与消息服务器建立连接，则通过此套接字将消息中转发送给朋友
            如果朋友没有注册到消息服务器，则向sso服务器发送请求查看朋友当前是否在线
            如果此时朋友不在线，则将消息暂存到cache中
            如果此时朋友在线，则会通过sso服务器获取与用户建立的套接字，通过此套接字向用户发送msg-inform请求，将消息暂存到cache中
            此请求会被用户的refresh线程读取，从而提醒用户打开对话框，和消息中转服务器建立套接字连接
    TalkService

3.utils
    ThreadManage
        记录当前消息服务器中的用户线程，负责线程集合的添加和删除操作
四.     QQCommons 公共类和方法
a.commons
    1.dao
        DbExcute
            static int executeUpdate(String sql) 根据sql语句执行更新，并且返回更新的行数
            static List<Record> executeQuery(String sql) 根据sql语句执行查询，返回封装后的结果集 
            static Record findBy(String sql) 根据sql语句查询单条结果
            static void close(ResultSet resu, Statement stmt, Connection conn) 关闭数据库连接，释放相应的资源
        RecordBuilder
            static final List<Record> builde(ResultSet rs)
                将数据库中的表数据每一行封装成Record，Record是一个<String,Object>的映射集
                然后将整个表的数据封装成List<Record>类型的数据集合
    2.entity
        User
            用户实体类，包括id、username、password、email、phone、avatar、salt、grade、state、oline、friendsIds实例字段
            该类实例化的对象主要用于记录用户的信息
        Message
            消息实体类，包括String msg、int friendsId、Date date对象
            该类的对象主要用于封装用户之间相互发送的消息内容

    3.model
        Record 数据库中每一行数据实例化一个Record对象，包括一个列名到对象的映射集
    4.utils
        DBUtils 数据库连接工具类
            利用properties文件初始化数据库连接池，提供连接数据库和关闭数据库的功能
        PropertiesUtils 
            static PropertiesUtils createPropertiesUtils(String fileName)   获取properties配置文件的操作对象
            setValue(String key, String value)  properties配置文件新增属性值
            String getValue(String key) 获取properties配置文件中的属性值
            Integer getInteger(String key) 获取properties配置文件中的int属性值
            Boolean getBoolean(String key) 获取properties配置文件中的Boolean属性值
        RecordClassCast
            通过反射利用数据库中的数据实例化出对应的对象
            具体方法是获取某一对象的class对象，然后获取所有的field，根据field的名称和返回值的类型获取
            对象中对应的setter方法，调用method.invok方法来完成对应属性的设置
            例如存在String name字段，首先String name=f.getName() Object o=map.get(name) 获取需要转入的属性对象和属性名称
            Method m=object.getClass().getMethod("set"+name,String.class) 获取对应方法
            m.invok(object,o)完成对应的属性装载 
        JacksonUtils
            主要负责Pojo实体对象和JSON字符串之间的转换
        JwtUtils
        ViewMessageBox
b.tcp
    Request
    Response
    SocketUtils
