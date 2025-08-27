# KoBridge: 다문화 초등학생의 한국어 능력 향상을 위한 AI 한국어 학습 웹앱
---

## **Table of Contents**

📢 [발표자료](https://youtu.be/WJCIIB1KSsg)

- [개요](#개요)
- [Skills](#skills)
- [ERD](#erd)
- [API 명세서](#api-명세서)
- [How to Run on Local](#how-to-run-on-local)

---

## 개요

### 한 줄 소개

다문화 가정 초등학교 저학년 아이들의 학습 부진과 학교 부적응에 주요 요인인 한국어 능력 부족을 해결하고자 만들어진 한국어 학습 서비스입니다.

### 주요 기능

1. 모국어 지원을 통한 보다 쉬운 학습 환경 제공
2. 채팅과 통화로 진행되는 친숙한 방식의 학습
3. 꾸준한 학습으로 이어지게 만드는 게이미피케이션

---

## Skills

| 구분              | 기술 스택                                                             |
|-----------------|-------------------------------------------------------------------|
| **언어 및 프레임워크**  | Java 17, Spring Boot 3.5.4                                        |
| **데이터베이스**      | MySQL, H2 (테스트용)                                                  |
| **캐시**          | Redis                                                             |
| **사용 API**      | Google API Client (구글 로그인), e-PreTX (발음 평가), OpenAI API (챗봇 응답) 등 |
| **배포 및 CI/CD**  | AWS EC2, RDS, S3, Route53, ALB, Docker, GitHub Actions            |
| **개발 도구/라이브러리** | Swagger, Lombok, JUnit                                            |
| **협업 도구**       | JIRA, GitHub, Discord                                             |

---

## Erd

<img width="1298" height="800" alt="image" src="https://github.com/user-attachments/assets/9ca8fb1d-dcb9-4e40-b58d-a66fcd07f1cc" />


<details><summary>user 엔티티 구성 이유</summary>

  <img width="378" height="445" alt="image" src="https://github.com/user-attachments/assets/da8ea904-d2fa-4935-8bf8-267d9bfb2238" style="width:300px; height:auto;"/>

회원가입으로 받을 때 언어(lang), 학교(school), 학년(grade), voice(목소리) 들은 정해진 선택지에 따라 Enum으로 구성하였다. lesson_id 의 경우 유저의 레벨이 어떤 단계인지 확인하기 위해 필요한 필드로, 그저 필드가 아닌 관계를 지은 이유는 user 조회 시, 바로 해당 레벨의 content로 이동이 가능하도록 서비스가 설계되었기 때문이다. 각 유저별로 다양한 레슨 단계을 거칠 수 있기에 user:lesson 을 1:다 의 관계로 설정했다.
</details>

<details><summary>lesson, lesson_sentence, lesson_chat 엔티티 구성 이유</summary>
  
<img width="770" height="492" alt="image" src="https://github.com/user-attachments/assets/9171a700-b8b7-4704-9b4e-85d46aa64ad3" />

1. `lesson`

lesson 은 제목과 주제가 있고, 기본적으로 3단계로 구성되며 각 단계별로 subTitle이 존재한다. 각 단계는 문장 따라하기, 문장 활용하여 대화하기, 레슨 평가하기로 구성되어 있다. 

2. `lesson_sentence`
   
한 레슨 별로 3가지의 문장이 주어지기에 lesson_sentence에 해당 부분을 저장하였다. lesson과 lesson_sentence를 1:다 관계로 설정하고, lesson entity에서 list 로 3가지 sentence를  lesson_sentence.number 필드를 참고해 지정된 순서에 맞춰서 가져올 수 있도록 설정해두었다. 

3. `lesson_chat`

한 레슨 별로 3가지 문장을 활용해서 대화하도록 지시문이 3가지 제시된다. 이는 lesson_chat에서 제공되며, 유도하는 문장에 대해 lseeon_sentence와 1:1 관계를 맺게 만들고, 해당 chat의 다음으로 제시되는 chat을 연결해주기 위해 lesson_chat 엔티티가 자신과의 1:1로 관계를 맺게 설정하였다. 

sentence와 다르게 number를 사용해 순서를 지정하지 않은 이유는 sentence의 순서와 chat의 순서가 담고 있는 의미가 달랐기 때문이다. sentence는 3가지 문장으로 각 문장이 흐름에 맞게 나타내져야 한다는 순서의 의미가 크게 담겨 있지만, 대화는 어떤 대화 다음으로 이어져야 하는 것인지의 의미가 담겨있다. 즉, 간단한 순서의 의미보다 어떤 인스턴스와 연결되는지의 명확성이 중요했다. 보다 의미에 맞게 저장하기 위해 sentenc는 number로 간단하게 순서만, chat은 다음 대화로 연결된다는 의미를 담은 자신과의 관계 설정을 하였다.  
</details>

<details><summary>lesson_legacy, lesson_legacy_sentence, lesson_legacy_chat, lesson_legacy_chat_summary, lesson_legacy_word  엔티티 구성 이유</summary>
  
<img width="704" height="610" alt="image" src="https://github.com/user-attachments/assets/365d661d-1e35-4b66-b09b-a250b06c7024" />

1. `lesson_legacy`

진행된 lesson에 대해 저장하기 위해 필요한 엔티티이다. 중간 저장 상태를 고려하여 state 를 넣어 어디까지 진행되었는지 확인할 수 있도록 하였다. 해당 레슨에 대한 평가의 경우 다른 추가 정보가 필요한 것이 아니기에 lesson_legacy 엔티티에 바로 저장할 수 있도록 해두었다.

2. `lesson_legacy_chat`
   
<img width="804" height="1748" alt="image" src="https://github.com/user-attachments/assets/b256ffad-ad84-4dff-a80f-5f783b6bcc00" style="width:300px; height:auto;" />

대화의 형식은 `질문-답변-추가 답변` 으로 정해져 있다. 또한 시스템의 질문으로 저장해야 되는 필드와 사용자의 답변으로 저장해야 하는 필드가 다르다. 따라서 질문 / 답변 엔티티를 나누어 구성하는 방법도 있었다. 서로의 id 를 참조해서 1개의 단계를 만들면 되었으나, 해당 방식으로 질문과 답변을 모두 저장한 이유는 첫번째로, lesson 대화 단계의 형식이 정해져 있기 때문이다. 항상 반복되는 형식이기에 답변이 2개가 오거나, 질문이 2개가 되는 변동의 상황에 유동적인 필드로 구성하지 않아도 된다고 판단했다. 두번째로, 데이터 로드를 위한 조인, id 조회 횟수 관련 문제이다. 질문-답변 엔티티로 나누어 저장했다면 분명 추후 확장성은 좋았을 것이다. 다만, 한번 불러왔을 때 각 테이블에 대한 조인 및 id 조회 횟수가 많아질 것을 고려하여 위처럼 설계하였다.

또한 각 대화는 3번 진행해야 하기에 다음 chat에 대해 위의 lesson_chat과 같은 방법으로 자기 자신과 1:1 관계를 가져 다음 대화에 대해 연결할 수 있또록 설정하였다.

3. `lesson_legacy_chat_summary`

<img width="804" height="1748" alt="image" src="https://github.com/user-attachments/assets/5b2372df-f86a-4e17-8772-ccdbe3f2f47d"  style="width:300px; height:auto;" />

대화가 완료되면 대화를 요약하기 위해 존재하는 대화 요약 엔티티이다. 요약을 그저 lesson_legacy에 넣지 않고 lesson_legacy 와 1:다로 관계를 설정한 이유는, 서비스에서 해당 데이터를 제공하는 형식을 고려했기 때문이다. 그저 긴 문장으로 요약을 제공하는 것이 아니라 대화 별로 짧게 나누어 제공하고 있었기에 따로 테이블을 분리하였다. 현재는 대화 횟수에 제한이 있기에 3가지만을 lesson_legacy에 필드로 넣을 수도 있었으나, 추후 확장 가능성을 고려하여 테이블로 분리해서 진행했다. 해당 부분을 설계하며 그저 문장과 문장 번역본을 저장하는 것인데 조인 성능에 대한 걱정이 있었으나 체계적인 구조를 잡는 것도 중요하다고 생각되어 이해가 잘 가도록 설계하고자 하였다.

4. `lesson_legacy_word`

대화에서 추출한 주요 단어를 저장하기 위한 엔티티이다. 단어, 단어의 번역본, 단어의 의미를 담고 있다. 단어의 번역본은 우선 사용자의 lang(모국어) 설정에 따라 저장되게 된다. 추후 사용자의 모국어 설정 변경에 대응이 힘들긴 하지만, 그것을 위해 모든 번역본을 저장하는 것은 저장공간 낭비라고 생각하여, 기획팀과 의논하여 모국어 변경 시 함께 같이 수정하기로 설정하였다.
 
5. `lesson_legacy_sentence`

사용자가 lesson의 첫번째 단계인 문장 따라하기에서 따라한 문장에 대해 저장하는 엔티티이다. 문장은 작성해도 괜찮고, 말해도 괜찮다. 말하기를 권장하지만, 서비스 사용성을 위해 추가된 작성하는 방향도 반영하여 audio를 nullable 로 설정하였으며, type(write, speack)을 통해 유효성 검증을 돕고 있다.



</details>

---


## API 명세서

[https://pribee.store/swagger-ui/index.html](https://pribee.store/swagger-ui/index.html)

<img width="1522" height="835" alt="image" src="https://github.com/user-attachments/assets/6b7e7fec-dc5a-47da-bd0b-0a4857943569" />

---


## How to Run on Local

본 서비스는 **Docker Compose**를 이용하여 실행할 수 있습니다.

### 1. 사전 준비

* [Docker](https://docs.docker.com/get-docker/) 설치
* [Docker Compose](https://docs.docker.com/compose/install/) 설치

> **확인 방법**

```bash
docker --version
docker compose version
```

버전이 정상 출력되면 설치 완료입니다.

---

### 2. docker-compose.yml 생성

아래 내용을 복사하여, 로컬에 `docker-compose.yml` 파일을 생성해주세요.

> `docker-compose.yml` 파일은 서비스와 Redis를 동시에 실행하기 위해 필요합니다.
> 제출한 파일과 함께 들어있습니다. 혹시 해당 파일을 확인하기 어렵다면, Redis 컨테이너를 따로 실행해 주시면 감사할 것 같습니다.
> 따로 Redis 컨테이너를 돌리시는 경우, container_name 이름을 redis 로 해주시면 됩니다.


---

### 3. 실행 방법

```bash
# 1. 최신 이미지 받아오기
docker pull yerim01/kobridge:latest

# 2. docker-compose 실행
docker compose up -d
```

실행이 완료되면 자동으로 **앱 + Redis** 두 컨테이너가 함께 실행됩니다.

---

### 4. 서비스 확인

* swagger 문서 접속:
  👉 [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

* Redis는 내부적으로 실행되며, 기본 포트 6379에서 동작합니다.

> 실행 중인 컨테이너 확인:

```bash
docker ps
```

---

### 5. 컨테이너 관리

서비스 종료 시:

```bash
docker compose down
```

다시 실행하고 싶으면:

```bash
docker compose up -d
```

