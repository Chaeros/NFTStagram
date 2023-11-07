# NFTStagram
블록체인 기반 SNS 애플리케이션

# 개발동기
개인과 기업 간의 저작권 싸움이 발생하면, 최초 소유권자가 개인일지라도 이를 증명하기가 어렵다는 기사를 보았습니다.<br>
이를 삭제, 수정이 불가능한 블록체인 기술을 활용하여 개인의 저작권 소유권을 강화하고자 SNS 형태의 블록체인 애플리케이션을 개발했습니다.<br>
사용자가 사진 또는 게시글을 NFT로 만들어 블록체인 네트워크에 저장하여<br>
혹시 생길지 모를 저작권 싸움에서 입증할 수 있는 증거로 사용하도록 만들고 싶어 개발을 시작했습니다.<br>

# 팀 구성 및 역할
![image](https://github.com/Chaeros/NFTStagram/assets/91451735/80010b76-e100-4de6-a4b8-6350d84d0357)


# 개발환경
UI : 안드로이드<br>
Database: Firebase<br>
외부 API : Kakao Klaytn API<br>

![image](https://github.com/Chaeros/NFTStagram/assets/91451735/d40b1209-af7f-4f35-bb54-a8e0184df265)

![image](https://github.com/Chaeros/NFTStagram/assets/91451735/f2023b1e-3421-4286-99aa-db1755555735)

# 기능
- 개인키를 사용한 로그인 기능(Klaytn 네트워크로부터 할당받은 암호화키)
- 전자지갑 생성
- 입장권(회원권 NFT 발행)
  - 특정 인물 또는 조직에서 개장한 갤러리를 방문하기 위해서 소유가 필요한 NFT이다.
  - 갤러리 생성과정에서 발행 여부를 선택할 수 있다. 
  - 발행하면 사용자들은 해당 갤러리에 일정금액을 
  - 지불하여 입장권 NFT를 구매하고 갤러리를 볼 수 있다.

- 갤러리 기능(발행된 NFT 입장권을 소유해야만 입장할 수 있도록 구현)
  - 자신의 계정 내에서 자신만의 갤러리 (온라인 전시회)를 개장할 수 있다.
  - 해당 갤러리에 들어가기 위해서는 NFT로 만들어진 입장권이 필요할 수도 있다.
-  자체 토큰 발행
  - 클레이로 NFT거래를 진행하면 일정 비율 만큼 자체 토큰을 얻을 수 있다.
  - 얻은 자체 토큰으로 클레이 대신 NFT 거래를 진행할 수 있다.

- 게시물 작성으로 NFT 생성
  - SNS에 게시물을 게시하면 해당 사진 및 설명이 바이트 코드로 변환되고,
  이를 블록 체인에 올림으로써 토큰을 생성한다.
  - 토큰으로 만들고 블록 체인에 넣기 싫은 경우에는 토글버튼으로 토큰화 여부를 선택할 수 있다.

- NFT 마켓 기능
  - 이용자들이 게시물을 서로 사고 팔 수 있는 마켓 기능을 제공(자체 발행한 암호화폐로 거래)

- 팔로우, 댓글 등 SNS 기본 기능 제공
  - 특정 게시물에 대한 의견을 나누기 위해 제공
  

# 시연 동영상

https://github.com/Chaeros/NFTStagram/assets/91451735/1b1513d9-0143-458b-b6b4-7a0ebc2a01f3

