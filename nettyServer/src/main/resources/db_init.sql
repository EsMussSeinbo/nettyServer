-- Create table
-- 一般违法
create table "TD_ILLEGALEVENT"
(
  id             VARCHAR2(50) not null,
  deviceid       VARCHAR2(20) not null,
  devicepos      VARCHAR2(100),
  laneno         number not null,
  eventtype      VARCHAR2(2) not null,
  eventtime      TIMESTAMP(3) not null,
  plateno        VARCHAR2(20) not null,
  platetype      VARCHAR2(2),
  platecolor     VARCHAR2(1),
  vehicletype    VARCHAR2(1),
  vehiclelogo    VARCHAR2(20),
  confidence     NUMERIC(9,6),
  direction      VARCHAR2(2),
  speed          number,
  rlontime       date,
  rlofftime      date,
  piccount       number not null,
  isvalid        VARCHAR2(1),
  inserttime     date,
  illegalid      VARCHAR2(6),
  policedevid    VARCHAR2(20),
  vehiclecolor   VARCHAR2(20),
  vehicleclass   VARCHAR2(3),
  limitspeed     number,
  maxlimitspeed  number,
  vehnewenerg    VARCHAR2(2),
  bigplateno     VARCHAR2(20),
  is_audit       VARCHAR2(1),
  datasource     VARCHAR2(1),
  classification VARCHAR2(1),
  picurl1        VARCHAR2(400),
  picname1       VARCHAR2(100),
  pictime1       date not null,
  picurl2        VARCHAR2(400),
  picname2       VARCHAR2(100),
  pictime2       date not null,
  picurl3        VARCHAR2(400),
  picname3       VARCHAR2(100),
  pictime3       date not null,
  picurl4        VARCHAR2(400),
  picname4       VARCHAR2(100),
  pictime4       date not null,
  videourl       VARCHAR2(400),
  audiourl       VARCHAR2(400)
)
;

-- Create table
-- 交通事件
create table TD_TRAFFICEVENT
(
  id             VARCHAR2(50) not null,
  datasource     varchar2(2) not null,
  deviceid       VARCHAR2(20) not null,
  devicepos      VARCHAR2(100),
  laneno         number not null,
  eventtype      VARCHAR2(2) not null,
  eventtime      TIMESTAMP(3) not null,
  starttime      date not null,
  endtime        date,
  videostarttime TIMESTAMP(3),
  videoendtime   TIMESTAMP(3),
  direction      VARCHAR2(2),
  piccount       number not null,
  picx           VARCHAR2(20),
  picy           VARCHAR2(20),
  isvalid        VARCHAR2(1),
  isabnormal     VARCHAR2(1),
  inserttime     date
)
;

-- Create table
-- 设备日志
create table TL_DEVICE
(
  file_name         VARCHAR2(500),
  deviceid          VARCHAR2(30),
  epdeviceid        VARCHAR2(30),
  record_time       date,
  ip_address        VARCHAR2(20),
  cpu_temp          NUMBER(10,2),
  cpu_fan_speed     NUMBER(10,2),
  free_memory       VARCHAR2(20),
  thread_count      Number(10),
  disk_space        VARCHAR2(10),
  nonuploadnum      number,
  camid             VARCHAR2(20),
  camera_state      VARCHAR2(100),
  camera_state_des  VARCHAR2(1000),
  videoid           VARCHAR2(20),
  video_state       VARCHAR2(100),
  video_state_des   VARCHAR2(1000),
  triggerid         VARCHAR2(20),
  trigger_state     VARCHAR2(100),
  trigger_state_des VARCHAR2(1000),
  lightid           VARCHAR2(20),
  light_state       VARCHAR2(100),
  light_state_des   VARCHAR2(100),
  is_normal         VARCHAR2(50),
  file_size         Number(15),
  file_createtime   date,
  file_analyse_time date,
  serverip          VARCHAR2(30),
  note              VARCHAR2(4000)
)
;

-- Create table
-- 操作日志
create table TL_MASTER
(
  id           varchar2(32) not null,
  deviceid     varchar2(20) not null,
  creationtime timestamp(3) not null,
  eventtime    date not null,
  eventdesc    varchar2(200) not null,
  note         varchar2(500),
  inserttime   date
)
;

-- Create table
-- 交通事件图片
create table TD_TRAFFICEVENTPIC
(
  id         VARCHAR2(32) not null,
  eventid    VARCHAR2(50) not null,
  picno      number not null,
  picname    VARCHAR2(100) not null,
  picpath    VARCHAR2(400) not null,
  captime    TIMESTAMP(3) not null,
  inserttime date
)
;

-- Create table
-- 交通事件视频
create table TD_TRAFFICEVENTVIDEO
(
  id             VARCHAR2(32) not null,
  eventid        VARCHAR2(50) not null,
  videoname      VARCHAR2(100) not null,
  videopath      VARCHAR2(400) not null,
  videostarttime TIMESTAMP(3) not null,
  videoendtime   TIMESTAMP(3) not null,
  inserttime     date
)
;

-- Create table
-- 设备表
CREATE TABLE TS_DEVICE
   (
   	DEVICEID VARCHAR2(20) NOT NULL ENABLE, 
	DEVICETYPE VARCHAR2(3) NOT NULL ENABLE, 
	DEVICENAME VARCHAR2(50) NOT NULL ENABLE, 
	ORGTYPE VARCHAR2(3), 
	ORGID VARCHAR2(10), 
	AREAID VARCHAR2(10), 
	ROADID VARCHAR2(10), 
	STAKENUM VARCHAR2(50), 
	DEVICEPOS VARCHAR2(100), 
	LONGITUDE VARCHAR2(50), 
	LATITUDE VARCHAR2(50), 
	DIRECTION VARCHAR2(3), 
	MASTERIP VARCHAR2(20), 
	INTERFACEIP VARCHAR2(20), 
	DEVICEIP VARCHAR2(20), 
	SHAFTSTAKENUM VARCHAR2(20), 
	SHAFTNAME VARCHAR2(20), 
	DEVICECOMPANY VARCHAR2(50), 
	QUEUELENGTHTHRESHOLD NUMBER(7,2), 
	STOPTIMETHRESHOLD NUMBER(3,0), 
	LOWSPEEDTHRESHOLD NUMBER(3,0), 
	TMINTERVAL NUMBER(3,0), 
	DEVICELOGINTERVAL NUMBER(3,0), 
	SYSLOGINTERVAL NUMBER(3,0), 
	STATE NUMBER(1,0) DEFAULT 1, 
	NOTE VARCHAR2(100), 
	DEVICEMODEL VARCHAR2(20), 
	DEVICESOURCE VARCHAR2(20), 
	OPERATETIME DATE DEFAULT sysdate, 
	OPERATOR VARCHAR2(32), 
	WATERMARKNAME VARCHAR2(50), 
	GISADDRESS VARCHAR2(50), 
	POSHEIGHT NUMBER, 
	HORIZONTALANGLE NUMBER, 
	CABRAGEANGLE NUMBER, 
	LANENUM NUMBER, 
	COVERAGEAREA VARCHAR2(20), 
	GATEWAYS VARCHAR2(20), 
	DEVICEMAC VARCHAR2(20), 
	LIGHTNUM NUMBER, 
	DRIVINGDIRECTION VARCHAR2(3), 
	UPSHAFTSTAKE VARCHAR2(20), 
	UPSHAFTDIS NUMBER, 
	UPJUNCNAME VARCHAR2(20), 
	UPJUNCDIS NUMBER, 
	DOWNSHAFTSTAKE VARCHAR2(20), 
	DOWNSHAFTDIS NUMBER, 
	DOWNJUNCNAME VARCHAR2(20), 
	DOWNJUNCDIS NUMBER, 
	BUILDSTATE VARCHAR2(1), 
	CONVERGEBOX VARCHAR2(100), 
	CONVERGETIME DATE, 
	DEVICETRANSTIME DATE, 
	DEVICEREMOVETIME DATE, 
	CONSTRUCTIONUNIT VARCHAR2(50), 
	POLICEDEVID VARCHAR2(20), 
	OBJNAME VARCHAR2(50), 
	FTPPORT NUMBER, 
	FTPUSER VARCHAR2(20), 
	FTPPASSWORD VARCHAR2(20), 
	POSPIC1 VARCHAR2(200), 
	CHECKPIC VARCHAR2(200), 
	DEVICERESOLUTION VARCHAR2(20), 
	DEVICENUM NUMBER, 
	DEVICEORIENTATION VARCHAR2(20), 
	DEVICEAREA VARCHAR2(20), 
	REGION VARCHAR2(20), 
	POSPIC2 VARCHAR2(200), 
	ATTACHMENT VARCHAR2(200), 
	DESIGNPIC VARCHAR2(200), 
	VIDEOPATH VARCHAR2(400), 
	LINKID VARCHAR2(20), 
	SECTID VARCHAR2(30), 
	POSPIC4 VARCHAR2(200), 
	MEMO1 VARCHAR2(200), 
	MEMO2 VARCHAR2(200), 
	MEMO3 VARCHAR2(200), 
	MEMO4 VARCHAR2(200), 
--	MEMO5 VARCHAR2(200), 
	MAINTENANCEUNIT VARCHAR2(50), 
	RAMPLANENO NUMBER, 
	BUSLANENO NUMBER, 
	BICYCLELANENO NUMBER, 
	OTHERLANENO NUMBER, 
	ISALL VARCHAR2(1), 
	 CONSTRAINT PK_TS_DEVICE PRIMARY KEY (DEVICEID)
   ) 
;