##### 简介
本程序可以预设一些列鼠标键盘操作指令，点击按钮后鼠标键盘依次执行预设的指令  
一般用于一键登录某些网站，免去手动输密码操作。
##### 文件说明
* hands-free.conf
配置文件，配置了每个按钮点击后该执行什么操作，该文件必须utf-8编码。
按钮名字顶格写，另起一行开始写按钮触发的命令流程，每行一条命令，每条命令前留两个空格。
* hands-free.jar  
程序主体，运行 java -jar hands-free.jar hands-free.conf 开始程序
* hands-free.exe  
就是hands-free.jar的启动器，点击后优雅启动程序
* start.cmd  
跟hands-free.exe一样的效果，启动时会有黑窗口一闪而过，不优雅
* start-with-cmd.cmd  
启动带控制台输出的程序，可以打印一些有用的日志

##### 可用的命令
注：本系统所有二维坐标，包括图片坐标，屏幕坐标，左上角为（x,y）= (0,0),向右 x 增大，向下 y 增大。  
所有配置会被用空格split后解析，如果命令或参数需要带空格，用\s转义，\ 用\\ 转义
如果发现mouseMove命令找不到图片位置了，一般需要重新截图

 * mouseMove 100 100  
        鼠标移动到屏幕坐标（100,100）的位置
 * mouseMove d:\img\0.png  +10 -20  
        查找图片d:\img\0.png在屏幕上的位置，记为（x,y）
        鼠标移动到（x+10,y-20)的位置
 * mouseBack 2  
        鼠标后退2步。
        例如某个按钮配有如下命令：  
        mouseMove 0 0  
        mouseMove 1 1  
        mouseMove 2 2  
        mouseBack 2  
        结束后鼠标会停在（1，1）位置  
 * mouseClick 5
        鼠标在当前位置快速连续单击5次，参数留空表示只单击一次
 * paste abc123456  
        相当于复制abc123456后，按键盘的ctrl+V粘贴到当前输入框
 * delay 3000  
        延时3000毫秒
 * exe C:\Program Files (x86)\Google\Chrome\Application\chrome.exe www.baidu.com  
        调用cmd执行后面的命令,上述命令会用chrome打开百度首页  
 * type [  
 * type enter  
        键盘按键，可用键有：  
        [ \ ]  , - . / ; = 0-9 A-Z  
        enter tab backSpace esc space  
        shift ctrl alt  
        pageUp pageDown end home insert delete printScreen  
        left up right down  
        小键盘：  
        n0 - n9  
        numLock  
        add 小键盘+  
        subtract 小键盘-  
        multiply 小键盘*  
        device 小键盘/  
        decimal 小键盘.  
* typeWith V ctrl
        按ctrl+V,也就是windows粘贴快捷键  
        两个参数，可用键同type
        
#### 高级教程
 配置文件可以定义一组命令，把相同的操作提取出来  
 比如  
 ```
 银联代扣
   exe C:\Program Files (x86)\Google\Chrome\Application\chrome.exe http://sfj.chinapay.com/dac/
   delay 1000
   mouseMove img\0.png 76 9
   mouseClick
   mouseBack 1
   paste 123456
   type tab
   paste 123456
   type tab
   paste 123456
   type tab
   type tab
微信公众平台
  exe C:\Program Files (x86)\Google\Chrome\Application\chrome.exe https://mp.weixin.qq.com/
  delay 1000
  mouseMove img\微信公众平台.png 2 2
  mouseClick
  mouseBack 1
  paste 123456
  type tab
  paste 123456
  type enter
 ```
 可以通过 define 命令改成如下
```
define login url img x y name pswd
  exe C:\Program Files (x86)\Google\Chrome\Application\chrome.exe url
  delay 1000
  mouseMove img x y
  mouseClick
  mouseBack 1
  paste name
  type tab
  paste pswd
银联代扣
   login http://sfj.chinapay.com/dac/ img\0.png 76 9 123456 123456
   type tab
   paste 123456
   type tab
   type tab
微信公众平台
  login https://mp.weixin.qq.com/ img\微信公众平台.png 2 2 123456 123456
  type enter
```