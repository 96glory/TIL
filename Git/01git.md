# Git & Github

## Git

### Git의 세가지 목적

1. version : 코드의 변경 사항을 추적할 수 있음
2. backup : 내 프로젝트의 과거 상태로 돌아갈 수 있음. 원격 저장소(github)와 로컬 저장소(내 컴퓨터) 개념을 활용함
3. collaborate : 원격 저장소에 push한 내용을 다른 개발자가 pull해서 받는다. 다른 개발자가 push한 내용을 내가 pull해서 받고 작업한다.

### Git의 종류

-   Github, TortoiseGit, Sourcetree, git-scm

## 버전 관리

### Windows에서 Git 설치

-   [설치 링크](https://git-scm.com/)

### init : 새 저장소(repository) 만들기

-   `git init .`

### commit : 버전(version) 만들기

-   ![unnamed](https://user-images.githubusercontent.com/52440668/91634915-331b2180-ea2f-11ea-8859-a9b9ea61c7bf.png)
    -   repository : 버전이 저장되어 있는 곳 (`.git` 폴더와 일맥상통)
    -   working tree : 아직 버전으로 인정되지 않은 파일
    -   staging area : 버전으로 만들려는 파일
-   `git status`
    -   untracked files : 아직 Git에게 관리해달라고 요청하지 않은 파일
    -   changes to be committed : Git에게 관리해달라고 요청한 파일
-   `git add [파일 명 혹은 *]`
    -   working tree에 있는 파일을 staging area로 올림
    -   untracked files를 changes to be committed 상태로 만듬
    -   해당 디렉토리 내에 있는 모든 파일을 버전관리하고 싶다면 \*를 사용하면 된다.
-   `git commit` or `git commit -m "Version Comment"`
    -   staging area에 있는 파일들을 기준으로 git에게 **버전을 만들라** 고 요청하고, staging area에 있는 파일들의 변경사항을 repository로 올린다.
-   `git commit -am "Version Comment"`
    -   `git add` + `git commit -m`
    -   단, untracked file은 staging area로 올라가지 않는다.
-   `git commit --amend`
    -   commit한 comment를 수정할 수 있다.
-   `git log`
    -   Git의 역사를 볼 때 사용하는 명령어
    -   `git log -p` : Git의 역사 + 코드 내부의 변화까지 프린트
    -   `git log --all --graph -- oneline`
        -   `--all` : 모든 브랜치의 역사가 보임
        -   `--graph` : 시각적으로 보임
        -   `--oneline` : 버전이 한 라인으로 보임

### diff : 버전 간의 차이점 비교

-   `git diff`

### checkout : 특정 버전으로 working tree를 변경시키기

-   `git checkout [commit ID]`
    -   commit ID는 `git log`를 통해서 알 수 있다.
-   `git checkout [branch name]`
    -   특정 브랜치로 working tree를 변경시킬 수 있다.

### reset : 버전을 삭제하기

-   `git reset [--hard]`
    -   가장 마지막 버전관리가 되어있는 상태로 돌아감.
-   `git reset [--hard] [commit ID]`
    -   **commit ID 돌아간다. commit version보다 최신 version이 삭제된다.** 시점에 주의하자.
    -   `--hard` : working area도 바꿔버린다.

### revert : 버전을 삭제하지 않으면서 되돌리는 방법

-   `git revert [commit ID]`
    -   **commit ID까지의 버전을 지우고, commit ID 직전 버전으로 바뀐다.** 그리고 기존 버전을 삭제하지 않고 새로운 버전을 생성한다.

## Branch & Conflict

### 브랜치의 사용법

-   `git branch`
    -   모든 브랜치의 목록을 보여줌
-   `git branch [branch name]`
    -   `branch name`이라는 브랜치 생성
-   ![캡처](https://user-images.githubusercontent.com/52440668/91638549-5358d980-ea4b-11ea-8e05-008051fe9f8f.JPG)
-   ![캡처3](https://user-images.githubusercontent.com/52440668/91638694-4983a600-ea4c-11ea-91dd-7d506cd01d60.JPG)

### 브랜치의 병합

-   ![캡처123](https://user-images.githubusercontent.com/52440668/91639076-f3643200-ea4e-11ea-903f-5f76fdc6da59.JPG)
    -   base : 병합하려는 것들의 공통적인 조상
    -   merge commit : 두 브랜치로부터 병합한 결과 도출된 commit

#### 서로 다른 파일을 병합하려고 할 때

-   branch o2 내용을 branch master로 병합하여 새로운 버전을 만들고 싶다!
    1. 먼저 HEAD가 master를 가리키고 있어야 한다.
    2. `git merge o2`
    3. ![캡처4](https://user-images.githubusercontent.com/52440668/91639264-48547800-ea50-11ea-942c-b05534bbcbdf.JPG)
    4. 만일 병합 이전으로 돌리고 싶다? `git reset --hard [commit ID]`

#### 같은 파일, 다른 부분을 병합하려고 할 때

-   branch o2 내용을 branch master로 병합하여 새로운 버전을 만들고 싶다!

    1. 먼저 HEAD가 master를 가리키고 있어야 한다.
    2. `git merge o2`
    3. 같은 파일에서 다른 부분이 수정되어 하나의 새로운 파일이 생성됨

        ```cpp
        // base
        # title
        content

        # title
        content
        ```

        ```cpp
        // o2 work 2
        # title
        o2 content

        # title
        content
        ```

        ```cpp
        // master work 2
        # title
        content

        # title
        master content
        ```

        ```cpp
        // merge result
        # title
        o2 content

        # title
        master content
        ```

#### 같은 파일, 같은 부분을 병합하려고 할 때

-   위와 같은 경우 git에서 conflict가 발생.
-   git에선 개발자가 conflict가 발생한 부분이 어디인지 알려주고, 개발자가 직접 수정해야 한다.
-   branch o2 내용을 branch master로 병합하여 새로운 버전을 만들고 싶다!
    1. 먼저 HEAD가 master를 가리키고 있어야 한다.
    2. `git merge o2`
    3. ![캡처5](https://user-images.githubusercontent.com/52440668/91639578-9c605c00-ea52-11ea-9af9-8d61ef2c1515.JPG)
    4. conflict를 해결한 뒤에, `git add *`
    5. `git commit`

### conflict을 처리하는 방법

-   ![캡처6](https://user-images.githubusercontent.com/52440668/91639791-f281cf00-ea53-11ea-84e8-98ce21ee90ec.JPG)
    -   2 way merge : 매우 엄격함
    -   3 way merge : 좀 더 유연함

### 외부 도구를 이용하여 병합하는 방법

-   p4Merge [다운로드 링크](https://www.perforce.com/downloads/visual-merge-tool)
-   `git mergetool`

## Backup

-   원격 저장소(remote repository)의 탄생
    -   여러 컴퓨터를 오가면서 각 컴퓨터(지역 저장소 : local repository)에서 `pull`하면 원격 저장소에서 데이터를 받아올 수 있고, 수정이 끝나면 원격 저장소에 `pull`할 수 있다. 코드를 백업할 수 있는 것이다.

### remote : 지역 저장소와 원격 저장소와 연결하기

-   `git remote -v`
    -   내 지역 저장소가 어떤 원격 저장소로 연결되어 있는지 확인

### push : 지역 저장소의 버전을 원격 저장소로 업로드하기

-   새 지역 저장소와 연결/업로드
    -   `echo "# my-repo" >> README.md`
    -   `git init`
    -   `git add README.md`
    -   `git commit -m "first commit"`
    -   `git remote add origin git주소`
    -   `git push -u origin master`
-   이미 존재하는 지역 저장소의 코드를 연결/업로드
    -   `git remote add origin git주소`
    -   `git push -u origin master`

### clone : 원격 저장소를 복제해서 지역 저장소로 만들기

-   `git clone git주소 [내가 원하는 directory 주소]`

### pull : 원격 저장소의 버전을 지역 저장소로 가져오기

-   `git pull`

## 협업

### 혼자 작업하기

-   [push 설명 참고](#push-:-지역-저장소의-버전을-원격-저장소로-업로드하기)

### 같이 작업하기 (github의 collaborator 기능)

-   github의 협업하고자 하는 repository - settings - collaborators & teams - collaborators - 협업하고자 하는 github ID 작성 후 Add collaborator - 초대된 사람에게 이메일이 전송됨 - 초대된 사람이 링크를 클릭하게 되면 Collaborators로 인정됨.
-   Collaborator의 권한
    -   Admin : read, clone, push, add
    -   Write : read, clone, push
    -   Read : read, clone

#### 다른 사용자끼리 push, pull

-   ![제목1없음](https://user-images.githubusercontent.com/52440668/91651284-e6365a00-eac5-11ea-864f-fb9862799f94.png)
-   교훈
    -   반드시 작업 전에 pull하자.
    -   pull 단위를 작게 하자.

### pull vs fetch

-   `git pull` -> `git commit` -> `git push`
-   `git fetch` -> `git merge FETCH_HEAD` -> `git commit` -> `git push`
-   **`git pull` = `git fetch` + `git merge FETCH_HEAD`**
    -   `FETCH_HEAD` : 가장 최근에 fetch한 내용과 merge할 버전이 적혀있음.
-   `git fetch`의 역할
    -   원격 브랜치를 가져온다.

### patch

-   오픈 소스를 다룰 때 사용하는 명령어
-   저장소 주인은 매번 오픈 소스에 대한 collaborator 기능을 사용하지 않고도 다른 사용자에게 권한을 줄 수 있다.
-   다른 사용자가 오픈 소스에 대한 어떤 수정사항이 필요하다고 생각 될때, 저장소 주인에게 알린다.
-   `git format-patch 다른_사용자의_입장에서_작성하기_전의_commit_ID`
    -   patch 파일이 만들어짐
    -   만들어진 patch 파일을 저장소 주인에게 보냄으로써 수정사항을 알려준다.
-   `git am -3 -i *.patch`
    -   `-3` : 3 way merge
    -   `-i` : patch 파일마다 이 내용을 적용할 것인지 물어보게 하는 옵션
    -   `*.patch` : 현재 디렉토리 내에 있는 모든 patch 파일에 대해서 merge 하겠다.

### Compare & Pull requests

-   내가 작업한 내용을 가져가 달라고 요청하는 것
-   github의 fork : 다른 사람의 원격 저장소를 내 원격 저장소로 가져옴
    -   fork한 사용자가 여러 commit과 push를 수행하고 나면, github의 repository에 Pull Request와 Compare 버튼이 활성화된다.
        -   Compare : fork된 저장소(base repository)와 fork한 저장소(head repository)를 비교함
        -   Pull Request : fork한 저장소의 달라진 점을 base repository 주인에게 알림.

## Cherry-pick & rebase

### cherry-pick의 개념과 기본 사용법

-   ![123캡처](https://user-images.githubusercontent.com/52440668/91652288-be002880-ead0-11ea-9cd7-171d8f42cd99.JPG)
-   branch topic의 commit인 **t2의 변화한 코드를** branch master에서 사용하고 싶을 때 cherry-pick을 사용한다!
    1. 병합될 branch로 이동한다.
        - `git checkout master`
    2. ` git cherry-pick 3b8d035`
        - `3b8d035`는 t2의 commit ID
    3. 충돌이 발생하지 않았다면
        - ![캡처3434](https://user-images.githubusercontent.com/52440668/91652398-066c1600-ead2-11ea-8440-8729fc0c8f15.JPG)
        - 서로 다른 파일을 cherry-pick했으므로 충돌이 발생하지 않는다.
    4. 충돌이 발생했다면
        - ![캡rewr처](https://user-images.githubusercontent.com/52440668/91652779-5698a780-ead5-11ea-9ea1-da785d69dfb8.JPG)
        - ![캡56565처](https://user-images.githubusercontent.com/52440668/91652794-6912e100-ead5-11ea-9d69-f95539b2b820.JPG)
        - `git cherry-pick --continue`

### Rebase의 개념과 기본 사용법

-   ![456](https://user-images.githubusercontent.com/52440668/91652472-a3c74a00-ead2-11ea-8e33-50b7f255ec31.JPG)
-   ![123](https://user-images.githubusercontent.com/52440668/91652470-a32eb380-ead2-11ea-92c8-5511b6abf02d.JPG)
-   branch topic과 branch master의 base를 바꾸고 싶을 때 rebase를 사용한다!
    -   기존 base는 `C`였지만 rebase를 통해 base가 `t2`로 바뀐 모습이다.
    -   결과는 선형적 구조를 띈다.
-   ![qwer](https://user-images.githubusercontent.com/52440668/91652539-5dbeb600-ead3-11ea-9907-e4ed8f5329e6.JPG)
-   위 사진에서 base인 init을 t3로 변경하려고 한다.
    1. 병합될 branch로 이동한다.
        - `git checkout master`
    2. 바꾸려는 commit인 t3의 branch로 rebase
        - `git rebase topic`
    3. 충돌이 발생하지 않았다면
        - ![vb처](https://user-images.githubusercontent.com/52440668/91652586-c148e380-ead3-11ea-9d3d-5cc757d128f6.JPG)
        - 서로 다른 파일을 rebase했으므로 충돌이 발생하지 않는다.
    4. 충돌이 발생했다면
        - ![11111](https://user-images.githubusercontent.com/52440668/91652916-8d22f200-ead6-11ea-9236-610361f9d2b2.JPG)
        - ![22222](https://user-images.githubusercontent.com/52440668/91652927-a166ef00-ead6-11ea-83b6-960743419cbe.JPG)
        - ![33333](https://user-images.githubusercontent.com/52440668/91652938-bb083680-ead6-11ea-8a57-9be3b0b3c1f1.JPG)

### cherry-pick vs rebase

-   ![ckdl](https://user-images.githubusercontent.com/52440668/91652636-1d136c80-ead4-11ea-9f74-e40b7c457988.JPG)

### rebase vs merge

-   ![123제목](https://user-images.githubusercontent.com/52440668/91653077-1686f400-ead8-11ea-869d-efea0deeb911.png)
