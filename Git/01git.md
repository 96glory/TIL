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
-   `git log`
    -   Git의 역사를 볼 때 사용하는 명령어
    -   `git log -p` : Git의 역사 + 코드 내부의 변화까지 프린트

### diff : 버전 간의 차이점 비교

-   `git diff`

### checkout : 특정 버전으로 working tree를 변경시키기

-   `git checkout [commit ID]`
    -   commit ID는 `git log`를 통해서 알 수 있다.
-   `git checkout master`
    -   특정 최신 브랜치로 working tree를 변경시킬 수 있다.

### reset : 버전을 삭제하기

-   `git reset [--hard]`
    -   가장 마지막 버전관리가 되어있는 상태로 돌아감.
-   `git reset [--hard] [commit ID]`
    -   **commit ID 돌아간다. commit version보다 최신 version이 삭제된다.** 시점에 주의하자.
    -   `--hard` : working area도 바꿔버린다.

### revert : 버전을 삭제하지 않으면서 되돌리는 방법

-   `git revert [commit ID]`
    -   **commit ID까지의 버전을 지우고, commit ID 직전 버전으로 바뀐다.** 그리고 기존 버전을 삭제하지 않고 새로운 버전을 생성한다.
