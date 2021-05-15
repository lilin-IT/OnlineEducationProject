//引入http模块
const http=require('http');
//创建服务器
http.createServer(function(request,response){
    //发送http头部
    //http状态值：200：ok
    //内容类型：text/plain
    response.writeHead(200,{'Content-Type':'text/html'});
    //发送响应数据"hello world"
    response.end('<h1>Hello Node.js Server</h1>');
}).listen(8888);
//终端打印如下信息
console.log('Server running at http://127.0.0.1:8888/');