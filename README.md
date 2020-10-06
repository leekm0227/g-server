# g-server in reactor-netty

구성
sessions <-> channels <-> apis

session : client 연결 관리
channel : 주요 data pub sub
api : db 관련 기능


문제점
reactor-netty tcp 연결시 첫 연결된 thread에 계속 연결됨
server to server 통신시 thread를 모두 사용하지 못하여 병목 발생

ex) 8thread 
session1(conn count : 1600, thread per request : 200) <-> channel(using thread : 2, thread per request : 1600) 
session2(conn count : 1600)
