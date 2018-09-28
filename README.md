# BooKookSecurities
부국증권
* * *
### 구현된 기능
1. 이메일 보내기
- Strings 클래스에 알림 메일 보낼 gmail주소 + 비밀번호를 입력
- EmailSender 객체를 호출해 보냄

2.ScheduledExecutorService 를 사용해서 주기적으로 Runnable thread를 delay시간마다 호출
- NotifyScheduler가 delay시간마다 NotifyThread를 호출

### 클래스
Strings.class
> 알림 메일 보내는 주소, 비밀번호, 세팅 파일 위치 정보

Controller.class
> fxml 화면 레이아웃과 bind 됨.

SettingsManager.class
> 전역 변수 + 세팅에 관련된 정보 불러올 수 있음

###해야할일
이지현
> TrayManager
>> 프로그램 종료 버튼을 누르면 종료되지 않고 시스템 트레이로 이동

>> 시작프로그램 등록

김지우
> NotifyThread
>> ReportManager를 통해 보고서 리스트를 불러오고 SettingManager에서 유저가 지정한 기간 보다 지난 리포트가
있는지 확인
>>> 기간 지난 리포트가 있으면 메일을 보낸다.

최성영
> ReportManager
>> 파일 저장위치는 SettingManager를 통해 불러옴

>> getReports 함수를 호출하면 Report Arraylist를 리턴

>> 각 보고서마다 경과된 시간 계산
>>> 제일 오래된 보고서를 구하는 함수

>> 리포트 파일 읽기 + 리포트 삭제

>> StringTokenizer 나 String.split을 사용해서 스페이스와 ""를 구문
>>> 파일 예) 026940 "부국철강" 20180105

이경돈
> SettingsManager
>> Strings 에서 세팅파일 위치를 불러와서 읽음

>> Settings.txt파일 포맷은 각 항목을 = 로 구별 report path="C:\app\folder"

>> 변경 버튼을 누르면 화면 textfield에 잇는 정보를 불러와 세팅파일을 수정



