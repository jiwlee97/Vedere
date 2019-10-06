# HTTP REST API
- 안드로이드의 httpclient와 쓰레드를 사용하여 직접 구현 가능<br/>
- 안드로이드의 메인 쓰레드의 경우 뷰 쓰레드로 직접적인 네트워크 통신을 허용하지 않음<br/>
-> 관련 라이브러리를 이용하여 통신(`Retrofit`)
## OkHttp
- Retrofit은 OkHttp 위에서 동작
- OkHttp만 사용하여 http 통신은 가능
- `Asynctask`를 이용하여 비동기로 실행<br/>
-> 여러 단점으로 인해 wrapper인 `Retrofit`을 이용
## Retrofit
- `OkHttp`의 wrapper 객체
- 초기 객체 생성 시 baseUrl, Converter, Intercepter 등 설정 가능
```java
new Retrofit.Builder()
    .baseUrl(baseURL)
    // json이외에 Converter를 통해 여러형태를 파싱
    .addConverterFac(GsonConverterFactory.create()) 
    //.client(client) <- OkHttp Intercept 사용
    .build();
```
- 인터페이스에서 어노테이션을 이용하여 URL 설정<br/>
--> TMap API의 경우 SDK로 제공하여 위에 내용을 쓸 필요가 없다.

# JUnit Test
- 자바 단위 테스트 모듈
### Dependnecy
```gradle
testImplementation 'junit:junit:4.12'
androidTestImplementation 'androidx.test:runner:1.1.1'
androidTestImplementation 'androidx.test.ext:junit:1.1.1'
androidTestImplementation'androidx.test.espresso:espresso-core:3.1.1'
androidTestImplementation"com.android.support.test:rules:1.02"    
```
- annotation을 이용한 설정
```java
@RunWith(AndroidJUnit4.class)
public class PermissionManagerTest {
    PermissionManager permissionManager;

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> intentTestRule = new ActivityTestRule<>(MainActivity.class);
 
    @Before
    public void setup() {
        MainActivity activity = intentTestRule.getActivity();
        permissionManager = new PermissionManager(activity);
    }

    @Test
    public void test() {
        assertEquals(permissionManager.check(Manifest.permission.ACCESS_FINE_LOCATION), true);
    }
}
```
- @Before은 테스트 수행 전 작업을 설정
- @Test는 테스트 코드
- @After은 테스트 후 작업을 설정
- assert관련 함수로 함수의 결과를 테스트
- @Rule은 테스트 클래스에서 동작 방식을 재정의하거나 추가<br/>
-> 임시폴더, 파일 등 생성, 외부 자원 초기화 반환, 에러 발생 시에도 진행 등

# Design Pattern
## MVC
- Model(POJO), View(XML), Controller(Activity)로 구성
- controller와 뷰의 결합으로 인해 테스트 및 확장에 불리

## MVP
- Model(POJO), View(XML+Acitivity/Fragment), Presenter(Interface)로 구성
- 비지니스 로직이 presenter에 추가되는 경향이 있어 유지보수가 어려워질 수 있음<br/>

-> 프로젝트가 크지 않으므로 MVC로 간단하게 작성 후 필요시 변환
- View : 음성 출력
- Controller : Activity Class

# Singleton pattern
- 하나의 클래스만 존재함을 보장
1. Eager Initalization
- 미리 생성해뒀다가 제공
> 장점: Thread-safe
> 단점:
항상 메모리를 차지하고 있음
```java
public class Singleton {
    private static final Singleton instance 
        = new Singleton();
    private Singleton() {}
    public static Sigleton getInstance() {
        return instance;
    }
}
```
2. Lazy Initialization(늦은 초기화 방식)
- 클래스의 인스턴스가 사용되는 시점에서 싱글톤 객체 생성하는 방식
> 장점: 필요할 때 생성
> 단점: Thread-unsafe
```java
public class Singleton{
    public static Singleton instance;
    private Singleton() {}
    public static Singleton getInstane() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
```

3. Initialization on Demand holder Idiom
- 클래스에 홀더를 두어 사용
```java
public class Singleton {
    private Singleton() {}
    private static class SingletonHolder {
        public static final Singleton INSTANCE = new Singleton();
    }
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

4. Enum 초기화
 - 모든 Enum type은 프로그램 내에서 한번 초기화 
 ```java
 public enum Singleton {
     INSTANCE;
     public void excute (String arg) {
         // ...
     }
 }
 ```