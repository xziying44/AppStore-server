????   4D
 V ? ?
  ?	 U ? ?	 U ? ? ?
  ? ?
  ? ?
  ? ?
  ?
  ?
  ? ?	 ? ?
  ?	 U ?	 U ? ? ? ?
 ? ?
 % ? \
 % ? ?
  ?	 U ? ?
 % ? - ? ? ? ? ? ? z
 % ? ? ? ?
 ) ? ? ? ? ? ? - ?	 ? ?
 ? ? ?
 1 ?
 ? ?
 U ? ?	 8 ?
 % ? ?
 8 ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ? ?
 ? ?
 ? ? ?
  ? ? ? ?
 S ? ? ? ? databaseService *Lcom/xziying/appstore/api/DatabaseService; token Ljava/lang/String; currentTime J verificationMap Ljava/util/Map; 	Signature VLjava/util/Map<Ljava/lang/String;Ljava/util/List<Lcom/alibaba/fastjson/JSONObject;>;>; verificationStr <init> ()V Code LineNumberTable LocalVariableTable this GLcom/xziying/appstorecloud/control/authorize/VerificationCodeCloudImpl; decryptData &(Ljava/lang/String;)Ljava/lang/String; 
base64Data k [B key !Ljavax/crypto/spec/SecretKeySpec; cipher Ljavax/crypto/Cipher; bytes 
Exceptions MethodParameters 
initialize ?(Lcom/xziying/appstore/api/DatabaseService;Ljava/lang/String;)V obj !Lcom/alibaba/fastjson/JSONObject; clazz o Ljava/lang/Object; verification json 
jsonObject verifications Ljava/util/List; e Ljava/lang/Throwable; LocalVariableTypeTable $Ljava/util/List<Ljava/lang/Object;>; StackMapTable ? ? ? n ? ? ? ? ? processTheMessage n(Ljava/lang/String;Lcom/xziying/appstore/plugIn/ProtocolEntry;Lcom/xziying/appstore/plugIn/domain/EventInfo;)I object s jsonObjects fromQQ by I Ljava/lang/Exception; protocolEntry +Lcom/xziying/appstore/plugIn/ProtocolEntry; 	eventInfo .Lcom/xziying/appstore/plugIn/domain/EventInfo; 3Ljava/util/List<Lcom/alibaba/fastjson/JSONObject;>; ? ? ? getVerification ()Ljava/lang/String; 
SourceFile VerificationCodeCloudImpl.java c d &java/util/concurrent/ConcurrentHashMap ^ _   b [ javax/crypto/spec/SecretKeySpec AES c  AES/ECB/PKCS5Padding javax/crypto/Cipher sun/misc/BASE64Decoder java/lang/String	
 c X Y Z [ ? ?PMIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANK7Xueba2RS/SncZR2643W8SL9zGlGzuXxKo200k9a07yVDpcSH8ycBRtq62aW5PtNx5HGcM5K41GoLGXRhK4cMNZhCmhEUov1o8M6mt/K5PLf5gTTX8GNEg110SIesYa7+js/ZGC3uinZYwgWYtmYchVGp3BQZGTFEVGGsjMMvAgMBAAECgYA4/AYL51FBDdf7y+dkBLehjMq3Sq7hTRJpc92BmGgp9T99+i8HWCP+di/s0f4s/Ezq7K9zsWOY13ZJPrshZ21XV+V9ttFoQ8yB74RgNiy7eeZOBgQWeoQ+CGF7RrNm0tMGdkDq0NlSrPcCnG91infgPxzBQUxLgHSSaY0pR61gcQJBAO2ntg3J/HrmuazisydRxXekcywNkgPgL3dgkWMEGAyr15H2e5uTyp+aS06xxMRwwYGNqVTYkQfeGFhD5NPY2psCQQDi/6G3yWuD2qHq8Kv4yLLskqLdqGIaxvTlupfyMzldLWa7clKKBH7z2bnpW7dIKSzlqgXoemKriOMnpKB8Raj9AkEA6A5gJRy67WMHoLoIB3fAIrAwSa7CighJMP7ZV97ygMT3DK6qSeLI8oldyWyp3srfGFq0IoYJL659BQrekMFpywJAfGsi54pl/LpL/2r0x4Kx10s0K4wMYaLlPjl86Qq8iV7GLT2nEfEO6HdRGB/mII45BpSfcmIKTPzVjLgGIrdHvQJAVMSxvfbWneRnzPbYDwy2vD7XqSEX155UKIj59r5qaAf+XzgZEPp5Rn6xcSJ/pYwhlknBWadfpq33kOhWvCjnhA== java/lang/Long \ ] ? com/alibaba/fastjson/JSONObject k ! )java/util/concurrent/CopyOnWriteArrayList"# java/util/List$!%&'()* java/lang/Throwable+ d ?, ? j k robotVersion-./0 java/lang/Integer123456 messageType74 messageSubtype84 source96 triggerActive:6 triggerPassive;6 messageNumber<6 	messageID=6 message>6 pointer?4@ ? allA! ?BC java/lang/Exception Ecom/xziying/appstorecloud/control/authorize/VerificationCodeCloudImpl java/lang/Object 8com/xziying/appstore/cloud/gateway/VerificationCodeCloud (com/xziying/appstore/api/DatabaseService java/util/Iterator )com/xziying/appstore/plugIn/ProtocolEntry ,com/xziying/appstore/plugIn/domain/EventInfo ([BLjava/lang/String;)V getInstance )(Ljava/lang/String;)Ljavax/crypto/Cipher; init (ILjava/security/Key;)V decodeBuffer (Ljava/lang/String;)[B doFinal ([B)[B !java/nio/charset/StandardCharsets UTF_8 Ljava/nio/charset/Charset; ([BLjava/nio/charset/Charset;)V "com/xziying/appstore/utils/RSAUtil decryptLong ([BLjava/lang/String;)[B parseObject 5(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject; get &(Ljava/lang/Object;)Ljava/lang/Object; 	longValue ()J getJSONArray 4(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONArray; iterator ()Ljava/util/Iterator; hasNext ()Z next ()Ljava/lang/Object; 	getString java/util/Map containsKey (Ljava/lang/Object;)Z put 8(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; add java/lang/System out Ljava/io/PrintStream; java/io/PrintStream println (Ljava/lang/Object;)V printStackTrace getJson TYPE Ljava/lang/Class; 	getObject 7(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object; intValue ()I setRobotVersion (I)V 	setFromQQ (Ljava/lang/String;)V setMessageType setMessageSubtype 	setSource setTriggerActive setTriggerPassive setMessageNumber setMessageID 
setMessage 
setPointer 	getFromQQ equals eventHandling 1(Lcom/xziying/appstore/plugIn/domain/EventInfo;)I ! U V  W    X Y     Z [     \ ]     ^ _  `    a   b [     c d  e   H     *? *? Y? ? *? ?    f            ! g        h i    j k  e       ??Y1TY2TY7TY4TY2TY0TY6TY9TY8TY	9TY
4TY4TY1TY3TY1TY8TM? Y,? 	N
? :-? ? Y? +? ? :? Y? ? ?    f       ' _ ( j * q + x - ? . g   >    ? h i     ? l [  _ 8 m n  j - o p  q & q r  ?  s n  t     S u    l    v w  e  &     ?*+? *,? +,?  N-? :? Y? ? :*? ? :*? ? ? ?  ? !:? " :? # ? ^? $ :		? %? J	? %:

&? ':*? ? ( ? *? ? )Y? *? + W*? ? , ? -
? . W???? /*? ? 0? N-? 2?  
 ? ? 1  f   Z    5  6 
 9  :  ; ( < . = 5 > F ? O @ k A s B z C ? D ? E ? G ? I ? J ? M ? K ? L ? N g   z  z @ x y 
 ? 7 z [  k O { | 	  ? } n   ? s n  ( ? ~ [  5 ?  y  O x ? ?  ?  ? ?    ? h i     ? X Y    ? Z [  ?     O x ? ?  ?   J ? X 	 ? ? ? ? ? ? ? ? ?  ? K ? ? ?? ? ?   ? ? ?  ? u   	 X   Z    ? ?  e  ?    f*? +? ( ?P*-? 3? 4:? :-5? 6? 7? 8? 9? :-;? 7? ? <-=? 6? 7? 8? 9? >-?? 6? 7? 8? 9? @-A? 7? ? B-C? 7? ? D-E? 7? ? F-G? 7? ? H-I? 7? ? J-K? 7? ? L-M? 6? 7? 8? 9? N*? +? , ? -:-? O:6? " :		? # ? F	? $ ? %:

;? 7? P? Q? 	6?  
;? 7? ? Q? 	6? ???? ,-? R ?? 
:? T?   Y] S  f   z    S  T  U  V 2 W B X V Y j Z z [ ? \ ? ] ? ^ ? _ ? ` ? c ? d ? e ? f g) h, i/ kC lF mI pL qZ u] s_ td v g   p  4 ? y 
 C ? [  <  y  ? m ? ?  ? g ? [  ? d ? ? _  ? ?   f h i    f z [   f ? ?   f ? ?  ?     ? m ? ?  ?   I ? ? 
 ? ? ? ? ? ? ? ? ?  ? / ?? ? ?   ? ? ? ?  B ? u    z   ?   ?    ? ?  e   /     *? ?    f       { g        h i    ?    ?