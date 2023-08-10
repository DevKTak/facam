# 과제1 : 장소 검색 Java 애플리케이션 개발

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

다음 작업 중 하나를 선택하여 구현하세요:

1. 특정 위치 주변의 지정된 반경 내에서 주유소를 검색하는 Java 어플리케이션을 생성합니다.
2. 특정 위치 주변의 지정된 반경 내에서 약국을 검색하는 Java 어플리케이션을 생성합니다.

## 과제 일정

- **강사**: 과제 안내 - 8월 18일 (금)
- **학생**: 과제 제출 - 8월 23일 (수) 23:59까지
- **멘토**: 과제 평가 - 8월 24일 (목)부터 8월 30일 (수)까지

## 문제 설명

### 단계 1: 카카오 API 키 획득

1. [카카오 개발자](https://developers.kakao.com)에 로그인합니다.
2. [시작 가이드](https://developers.kakao.com/docs/latest/en/getting-started/app)에 따라 어플리케이션을 생성합니다.
3. 나중에 사용할 REST API 키를 메모해 둡니다.

### 단계 2: 로컬 REST API 사용

1. [로컬 REST API 문서](https://developers.kakao.com/docs/latest/en/local/dev-guide)를 참고하여 키워드와 카테고리 검색을 개발합니다.
2. 요청과 응답 구조에 대한 예제 코드를 확인합니다.

### 단계 3: Java 어플리케이션 구현

1. 키보드로 특정 위치 키워드와 검색 반경을 입력합니다.
2. 입력한 키워드를 기반으로 위도와 경도를 추출합니다.
3. 추출한 위치를 사용하여 입력한 반경 내에서 주유소나 약국을 검색합니다.
4. 검색 결과에서 원하는 정보를 추출하여 표시합니다.
5. 상위 10개 결과에 대한 추출된 데이터를 표시합니다.

## 예시 입력 및 출력

### 입력 화면

위치 키워드를 입력하세요:

검색 반경을 입력하세요 (1000:1km):

### 출력 화면

```cmd
입력한 위치 키워드:
검색 반경:
```

**주유소 검색 결과**

```cmd
- 장소 URL(지도 위치):
- 사업명:
- 주소:
- 전화번호:
- 거리(km):
```

**약국 검색 결과**

```cmd
- 장소 URL(지도 위치):
- 사업명:
- 주소:
- 전화번호:
- 거리(km):
```

## 의존성

- Java 8 이상

## 평가 기준

- 제시된 기한 내 제출 (5점)
- 문제 조건 준수한 코드 (5점)
- 제안된 기능 작동 여부 (5점)
