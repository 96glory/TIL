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
