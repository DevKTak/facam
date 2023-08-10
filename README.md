> # 과제1 : 위치기반 장소 검색 Java 애플리케이션 개발
> ```(Location Based Place Search Java Application Development)```

이 Java 어플리케이션은 입력에 기반하여 특정 위치 내의 장소를 검색할 수 있도록 합니다.

자신의 위치를 기반으로 특정위치에 있는 장소를 검색하여 출력하는 자바 애플리케이션을 개발 할 수 있다.

Kakao 로컬 REST API중 키워드로 장소 검색하기, 카테고리로 장소 검색하기 API를 활용 할 수 있다.

## 목차

- [작업 옵션](#작업-옵션)
- [과제 일정](#과제-일정)
- [문제 설명](#문제-설명)
- [예시 입력 및 출력](#예시-입력-및-출력)
- [의존성](#의존성)
- [평가 기준](#평가-기준)

## 작업 옵션

```다음 작업 중 하나를 선택하여 구현하세요```

1. 특정 위치(키워드) 주변의 지정된 반경 내에서 **주유소**를 검색하는 Java 어플리케이션을 개발합니다.
2. 특정 위치(키워드) 주변의 지정된 반경 내에서 **약국**을 검색하는 Java 어플리케이션을 개발합니다.

## 과제 일정

- **강사**: 과제 안내 - 8월 18일 (금)
- **학생**: 과제 제출 - 8월 23일 (수) 23:59까지
- **멘토**: 과제 평가 - 8월 24일 (목)부터 8월 30일 (수)까지

## 문제 설명

### 단계 1: 카카오 API 키 획득

1. [카카오 개발자](https://developers.kakao.com)에 로그인합니다.
2. [시작 가이드](https://developers.kakao.com/docs/latest/ko/getting-started/app)에 따라 어플리케저 실행시 보여지는 화면 예
![image](https://github.com/FastCampusKDTBackend/KDT_Y_BE_Java_Assignment1/assets/15371961/e22d285d-07f6-4e8a-8de9-8d8cde1c145d)
![image](https://github.com/FastCampusKDTBackend/KDT_Y_BE_Java_Assignment1/assets/15371961/90269aa3-eb9c-4355-b7ea-11ffb0adf58e)

## 의존성

- Java 8 이상
- httpclient, json API
```pom.xml
<dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpclient</artifactId>
        <version>4.5.13</version>
</dependency>
<dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20210307</version>
</dependency>
```
## 평가 기준

- 제시된 기한 내 제출 (5점)
- 문제 조건 준수한 코드 (5점)
- 제안된 기능 작동 여부 (5점)

1. 입출력 화면이 잘 설계 되었는가?
2. JSON 데이터 잘 파싱하여 목록을 출력하였는가?
3. 번경에 따라 데이터가 잘 출력이 되는가?
4. 장소가 브라우져에 잘 표시되는가?

## 제출내용

- 소스코드 제출
- 입출력 실행화면 캡처 제출
1. 소스코드에 ```과제1``` 폴더를 만들고 ```과제1``` 폴더에 아래 2개의 이미지 파일을 추가할 것
2. 입력화면 캡처
3. 출력화면 챕처

** 문의사항은 슬랙에 요쳥해주세요 **
