-------------------
### **服务器地址**
跟踪服务器:**120.77.41.37**
存储服务器:**120.77.41.37**

### **服务器环境**
环境:**CentOS 7**
用户: **root**
数据目录:**/fastdfs** (<font color="red" size = "1px">注:数据目录按你的数据盘挂载路径而定</font>)

### **安装包**
**FastDFS v5.05**
**libfastcommon-master.zip**(<font color="red" size = "1px">是从 FastDFS 和 FastDHT 中提取出来的公共 C 函数库</font>)
**fastdfs-nginx-module_v1.16.tar.gz**
**nginx-1.6.2.tar.gz**
**fastdfs_client_java._v1.25.tar.gz**
源码地址:https://github.com/happyfish100/ 
下载地址:http://sourceforge.net/projects/fastdfs/files/ 
官方论坛:http://bbs.chinaunix.net/forum-240-1.html

 

###  **所有跟踪服务器和存储服务器均执行如下操作:**
####编译和安装所需的依赖包:

> <font color="red" size = "1px"># yum install make cmake gcc gcc-c++</font>


 

####**安装 libfastcommon:**

 - 上传或下载 libfastcommon-master.zip 到/usr/local/src 目录
 - 解压:

> <font color="red" size = "1px"># cd /usr/local/src/</font>
> <font color="red" size = "1px"># unzip libfastcommon-master.zip </font>
> <font color="red" size = "1px"># cd libfastcommon-master</font>

 - 编译、安装:
 

> <font color="red" size = "1px"># ./make.sh</font>
> <font color="red" size = "1px"># ./make.sh install</font>
> <font color="A1A9CE" size = "1px">libfastcommon 默认安装到了</font>
> <font color="A1A9CE" size = "1px">/usr/lib64/libfastcommon.so</font>
> <font color="A1A9CE" size = "1px">/usr/lib64/libfdfsclient.so</font>



 - 因为 FastDFS 主程序设置的 lib 目录是/usr/local/lib，所以需要创建软链接:
 

> <font color="red" size = "1px"># ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so</font>
> <font color="red" size = "1px"># ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so</font>
> <font color="red" size = "1px"># ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so</font>
> <font color="red" size = "1px"># ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so</font>
####**安装 FastDFS**

 - 上传或下载 FastDFS 源码包(FastDFS_v5.05.tar.gz)到 /usr/local/src 目录
 - 解压:
 

> <font color="red" size = "1px"># cd /usr/local/src/</font>
> <font color="red" size = "1px"># tar -zxvf FastDFS_v5.05.tar.gz</font>
> <font color="red" size = "1px"># cd FastDFS</font>

 - 编译、安装(编译前要确保已经成功安装了 libfastcommon)
 

> <font color="red" size = "1px"># ./make.sh</font>
> <font color="red" size = "1px"># ./make.sh install</font>
> <font color="A1A9CE" size = "1px">采用默认安装的方式安装,安装后的相应文件与目录:</font>
> <font color="A1A9CE" size = "1px">A、服务脚本在:</font>
> <font color="A1A9CE" size = "1px">/etc/init.d/fdfs_storaged</font>
> <font color="A1A9CE" size = "1px">/etc/init.d/fdfs_tracker</font>
> <font color="A1A9CE" size = "1px">B、配置文件在(样例配置文件):</font>
> <font color="A1A9CE" size = "1px">/etc/fdfs/client.conf.sample</font>
> <font color="A1A9CE" size = "1px">/etc/fdfs/storage.conf.sample</font>
> <font color="A1A9CE" size = "1px">/etc/fdfs/tracker.conf.sample</font>
> <font color="A1A9CE" size = "1px">C、命令工具在/usr/bin/目录下的:</font>
> <font color="A1A9CE" size = "1px">fdfs_appender_test</font>
> <font color="A1A9CE" size = "1px">fdfs_appender_test1</font>
> <font color="A1A9CE" size = "1px">fdfs_append_file</font>
> <font color="A1A9CE" size = "1px">fdfs_crc32</font>
> <font color="A1A9CE" size = "1px">......</font>

 - 因为 FastDFS 服务脚本设置的 bin 目录是/usr/local/bin，但实际命令安装在/usr/bin，可以进入 /user/bin 目录使用以下命令查看 fdfs 的相关命令:
 

> <font color="red" size = "1px"># cd /usr/bin/</font>
> <font color="red" size = "1px"># ls | grep fdfs</font>

 - 因此需要修改 FastDFS 服务脚本中相应的命令路径，也就是把/etc/init.d/fdfs_storaged 和/etc/init.d/fdfs_tracker 两个脚本中的/usr/local/bin 修改成/usr/bin:
 

> <font color="red" size = "1px"># vi fdfs_trackerd</font>

 - 使用查找替换命令进统一修改:<font color="red" size = "1px">%s+/usr/local/bin+/usr/bin</font>
 

> <font color="red" size = "1px"># vi fdfs_storaged</font>

 - 使用查找替换命令进统一修改:<font color="red" size = "1px">%s+/usr/local/bin+/usr/bin</font>


----------


### **配置 FastDFS 跟踪器**
#### **复制 FastDFS 跟踪器样例配置文件,并重命名:**

> <font color="red" size = "1px"># cd /etc/fdfs/</font>
> <font color="red" size = "1px"># cp tracker.conf.sample tracker.conf</font>
#### **编辑跟踪器配置文件:**
> <font color="red" size = "1px"># vi /etc/fdfs/tracker.conf</font>
##### **修改的内容如下:**
> disabled=false
>  port=22122
>  base_path=/fastdfs/tracker 
>  (其它参数保留默认配置，具体配置解释请参考官方文档说明: http://bbs.chinaunix.net/thread-1941456-1-1.html )

#### **创建基础数据目录(参考基础目录 base_path 配置):**

> <font color="red" size = "1px"># mkdir -p /fastdfs/tracker</font>
#### **防火墙中打开跟踪器端口(默认为 22122):**
> <font color="red" size = "1px"># vi /etc/sysconfig/iptables</font>
##### **添加如下端口行:**
       -A INPUT -m state --state NEW -m tcp -p tcp --dport 22122 -j ACCEPT
##### **重启防火墙:**
> <font color="red" size = "1px"># service iptables re
	
	
	t</font>
#### **启动 Tracker:**
> <font color="red" size = "1px"># /etc/init.d/fdfs_trackerd start</font>
##### **<font color="red" size = "1px">(初次成功启动，会在/fastdfs/tracker 目录下创建 data、logs 两个目录)</font>**
##### 查看 FastDFS Tracker 是否已成功启动:
> <font color="red" size = "1px"># ps -ef | grep fdfs</font>
#### **关闭 Tracker:**
> <font color="red" size = "1px"># /etc/init.d/fdfs_trackerd stop</font>
#### **设置 FastDFS 跟踪器开机启动:**
> <font color="red" size = "1px"># vi /etc/rc.d/rc.local</font>
##### **添加以下内容:**
> <font color="A1A9CE" size = "1px">## FastDFS Tracker</font>
> <font color="A1A9CE" size = "1px">/etc/init.d/fdfs_trackerd start</font>


----------


### **配置 FastDFS 存储**
#### **复制 FastDFS 存储器样例配置文件,并重命名:**
> <font color="red" size = "1px"># cd /etc/fdfs/</font>
> <font color="red" size = "1px"># cp storage.conf.sample storage.conf</font>
#### **编辑存储器样例配置文件:**
> <font color="red" size = "1px"># vi /etc/fdfs/storage.conf</font>
##### **修改的内容如下:**
> <font color="A1A9CE" size = "1px">disabled=false</font>
> <font color="A1A9CE" size = "1px">port=23000</font>
> <font color="A1A9CE" size = "1px">base_path=/fastdfs/storage</font>
> <font color="A1A9CE" size = "1px">store_path0=/fastdfs/storage</font>
> <font color="A1A9CE" size = "1px">tracker_server=120.77.41.37:22122</font>
> <font color="A1A9CE" size = "1px">http.server_port=8888</font>
> <font color="A1A9CE" size = "1px">(其它参数保留默认配置，具体配置解释请参考官方文档说明: http://bbs.chinaunix.net/thread-1941456-1-1.html )</font>
> <font color="A1A9CE" size = "1px"></font>
#### **创建基础数据目录(参考基础目录 base_path 配置):**
> <font color="red" size = "1px"># mkdir -p /fastdfs/storage</font>
#### **防火墙中打开存储器端口(默认为 23000):**
<font color="red" size = "1px">参考上面修改防火墙</font>
#### **启动 Storage:**
> <font color="red" size = "1px"># /etc/init.d/fdfs_storaged start</font>
##### <font color="red" size = "1px">初次成功启动，会在/fastdfs/storage 目录下创建 data、logs 两个目录)</font>
#### **关闭 Storage:**
> <font color="red" size = "1px"># /etc/init.d/fdfs_storaged stop</font>
#### **设置 FastDFS 存储器开机启动:**
> <font color="red" size = "1px"># vi /etc/rc.d/rc.local</font>
##### **添加:**
> <font color="A1A9CE" size = "1px">## FastDFS Storage</font>
> <font color="A1A9CE" size = "1px">/etc/init.d/fdfs_storaged start</font>


----------


### **四、文件上传测试**
#### **修改 Tracker 服务器中的客户端配置文件:**
> <font color="red" size = "1px"># cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf</font>
> <font color="red" size = "1px"># vi /etc/fdfs/client.conf</font>
> <font color="A1A9CE" size = "1px">修改内容如下:</font>
> <font color="A1A9CE" size = "1px">base_path=/fastdfs/tracker</font>
> <font color="A1A9CE" size = "1px">btracker_server=120.77.41.37:22122</font>
#### **执行如下文件上传命令:**
> <font color="red" size = "1px"># /usr/bin/fdfs_upload_file /etc/fdfs/client.conf /usr/local/src/FastDFS_v5.05.tar.gz</font>
##### **返回 ID 号:group1/M00/00/00/wKFHOASIOCO43242342XO.tar.gz**
##### **能返回以上文件 ID，说明文件上传成功)**


----------


### **在每个存储节点上安装 nginx**
#### **fastdfs-nginx-module 作用说明:**
	FastDFS 通过 Tracker 服务器,将文件放在 Storage 服务器存储，但是同组存储服务器之间需要进入文件复制，有同步延迟的问题。假设 Tracker 服务器将文件上传到了 120.77.41.37，上传成功后文件 ID 已经返回给客户端。此时 FastDFS 存储集群机制会将这个文件同步到同组存储 120.77.41.37，在文件还 没有复制完成的情况下，客户端如果用这个文件 ID 在 120.77.41.37 上取文件,就会出现文件无法访问的 错误。而 fastdfs-nginx-module 可以重定向文件连接到源服务器取文件,避免客户端由于复制延迟导致的 文件无法访问错误。(解压后的 fastdfs-nginx-module 在 nginx 安装时使用)
#### **上传 fastdfs-nginx-module_v1.16.tar.gz 到/usr/local/src**
#### **解压**
> <font color="red" size = "1px"># cd /usr/local/src/</font>
> <font color="red" size = "1px"># tar -zxvf fastdfs-nginx-module_v1.16.tar.gz</font>
#### **修改 fastdfs-nginx-module 的 config 配置文件:**
> <font color="red" size = "1px"># cd fastdfs-nginx-module/src</font>
> <font color="red" size = "1px"># vi config</font>
> <font color="A1A9CE" size = "1px">CORE_INCS="$CORE_INCS /usr/local/include/fastdfs /usr/local/include/fastcommon/"</font>
> 修改为:
> <font color="green" size = "1px">CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"</font>
> <font color="red" size = "1px">注意:这个路径修改是很重要的，不然在 nginx 编译的时候会报错的)</font>
#### **上传当前的稳定版本 Nginx(nginx-1.6.2.tar.gz)到/usr/local/src 目录:**
#### **安装编译 Nginx 所需的依赖包:**
> <font color="red" size = "1px"># yum install gcc gcc-c++ make automake autoconf libtool pcre* zlib openssl openssl-devel</font>
#### **编译安装 Nginx(添加 fastdfs-nginx-module 模块):**
> <font color="red" size = "1px"># cd /usr/local/src/</font>
> <font color="red" size = "1px"># tar -zxvf nginx-1.6.2.tar.gz</font>
> <font color="red" size = "1px"># cd nginx-1.6.2</font>
> <font color="red" size = "1px"># ./configure --add-module=/usr/local/src/fastdfs-nginx-module/src</font>
> <font color="red" size = "1px"># make && make install</font>
#### **复制 fastdfs-nginx-module 源码中的配置文件到/etc/fdfs 目录，并修改:**
> <font color="red" size = "1px"># cp /usr/local/src/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs/</font>
> <font color="red" size = "1px"># vi /etc/fdfs/mod_fastdfs.conf</font>
> 修改以下配置:
> <font color="A1A9CE" size = "1px">connect_timeout=10</font>
> <font color="A1A9CE" size = "1px">base_path=/tmp</font>
> <font color="A1A9CE" size = "1px">tracker_server=120.77.41.37:22122</font>
> <font color="A1A9CE" size = "1px">storage_server_port=23000</font>
> <font color="A1A9CE" size = "1px">group_name=group1</font>
> <font color="A1A9CE" size = "1px">url_have_group_name = true</font>
> <font color="A1A9CE" size = "1px">store_path0=/fastdfs/storage</font>
#### **复制 FastDFS 的部分配置文件到/etc/fdfs 目录:**
> <font color="red" size = "1px"># cd /usr/local/src/FastDFS/conf</font>
> <font color="red" size = "1px"># cp http.conf mime.types /etc/fdfs/</font>
#### **在/fastdfs/storage 文件存储目录下创建软连接,将其链接到实际存放数据的目录:**
> <font color="red" size = "1px"># ln -s /fastdfs/storage/data/ /fastdfs/storage/data/M00</font>
#### **配置 Nginx:**
##### 简洁版 nginx 配置样例:
>user root; 
>worker_processes 1;
> events {
> worker_connections  1024;
>}
>http {
>    include       mime.types;
>   default_type  application/octet-stream;
>    sendfile        on;
>    keepalive_timeout  65;
>    server {
>        listen       8888;
>        server_name  localhost;
>        location ~/group([0-9])/M00 {
>            #alias /fastdfs/storage/data;
>            ngx_fastdfs_module;
>        }
>        error_page   500 502 503 504  /50x.html;
>        location = /50x.html {
> root html; }
> } } 

    A、8888 端口值是要与/etc/fdfs/storage.conf 中的 http.server_port=8888 相对应，因为 http.server_port 默认为 8888,如果想改成 80，则要对应修改过来。
	B、Storage 对应有多个 group 的情况下，访问路径带 group 名，如/group1/M00/00/00/xxx，
#### **对应的 Nginx 配置为:**

> <font color="red" size = "1px">location ~/group([0-9])/M00 {</font>
> <font color="red" size = "1px">ngx_fastdfs_module;</font>
> <font color="red" size = "1px">}</font>

	C、如查下载时如发现老报 404，将 nginx.conf 第一行 user nobody 修改为 user root 后重新启动。
#### **防火墙中打开 Nginx 的 8888 端口**
<font color="red" size = "1px">参考前边修改防火墙端口</font>
#### **启动 Nginx:**

> <font color="red" size = "1px"># /usr/local/nginx/sbin/nginx</font>
> <font color="A1A9CE" size = "1px">ngx_http_fastdfs_set pid=xxx</font>
> (重启 Nginx 的命令为:<font color="red" size = "1px">/usr/local/nginx/sbin/nginx -s reload</font>)
#### **通过浏览器访问测试时上传的文件:**
 http://120.77.41.37:8888/group1/M00/00/00/rBIvKFll_-iAKwK9AAIpzOP593E159.jpg?token=f42b765ca5101374e831b123ce4f8fbe&ts=1499856871
#### **浏览器成功访问** 
 ![上传成功](http://img.blog.csdn.net/20170713104317052?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZmFuNzUyNTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


----------




### FastDFS与 Spring 整合
#### **xml配置文件:**
``` xml
<bean id="fastDFSFactory" class="FastDFSTemplateFactory" init-method="init">
    <!--连接超时的时限，单位为秒-->
    <property name="g_connect_timeout" value="60"/>
    <!--网络超时的时限，单位为秒-->
    <property name="g_network_timeout" value="80"/>
    <!--防盗链配置-->
    <property name="g_anti_steal_token" value="true"/>
    <property name="g_secret_key" value="FastDFS1234567890"/>
    <property name="poolConfig">
      <bean class="PoolConfig">
        <!--池的大小-->
        <property name="maxTotal" value="100"/>
        <!--连接池中最大空闲的连接数-->
        <property name="maxIdle" value="10"/>
      </bean>
    </property>
    <!--tracker的配置 ","逗号分隔-->
    <property name="tracker_servers" value="120.77.41.37:22122"/>
    <!--HTTP访问服务的端口号-->
    <property name="g_tracker_http_port" value="8080"/>
    <!--nginx的对外访问地址，如果没有端口号，将取g_tracker_http_port配置的端口号 ","逗号分隔-->
    <property name="nginx_address" value="120.77.41.37:8888"/>
  </bean>

  <!--注入模板类-->
  <bean id="fastDFSTemplate" class="FastDFSTemplate">
    <constructor-arg ref="fastDFSFactory"/>
  </bean>
```





 

	


