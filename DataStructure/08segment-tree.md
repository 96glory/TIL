# Segment Tree

> [출처](https://yabmoons.tistory.com/431)

### 세그먼트 트리

- 특정 구간 내에 어떤 규칙이 존재할 때, 그 규칙에 맞게 값을 기억하기 위한 트리
    - 규칙은 특정 구간 내 합, 최솟값, 최댓값 등이 있다.
- ![다운로드](https://user-images.githubusercontent.com/52440668/93298759-57268300-f7e3-11ea-9985-b9d2f2c33f39.png)
    - 리프 노드는 배열의 값을 저장한다.
    - 리프가 아닌 노드는 특정 구간 내 규칙에 맞는 값을 저장한다.
- ![다운로드 (1)](https://user-images.githubusercontent.com/52440668/93298850-f91f8d00-f82e-11ea-8b63-0e85c17b7f33.png)
    - 다음은 5개의 원소를 갖고, 규칙은 특정 구간 내 합인 세그먼트 트리다.
- 크기가 N인 배열이 존재할 때,
    - 트리의 높이 = ![image](https://user-images.githubusercontent.com/52440668/93299123-8367f100-f82f-11ea-903e-fcbb6ab89044.png)
    - 세그먼트 트리를 구현하기 위한 노드의 수 = ![image2](https://user-images.githubusercontent.com/52440668/93299208-ab575480-f82f-11ea-930d-122ffb47233a.png)

### 세그먼트 트리 만들기

- 수도코드
    1. 주어진 범위를 절반으로 나눈다.
    2. 나눠진 2개의 범위에 대해서 왼쪽 범위에 대한 재귀 호출
    3. 나눠진 2개의 범위에 대해서 오른쪽 범위에 대한 재귀 호출
    4. 왼쪽 범위와 오른쪽 범위로부터 나온 값으로 규칙 적용 후 세그먼트 트리에 저장
    5. 1 ~ 4의 과정을 반복
- 세그먼트 트리의 규칙이 특정 구간의 합인 경우 세그먼트 트리 만들기
    ```cpp
    // 트리의 왼쪽 자식 노드 접근 : n * 2
    // 트리의 오른쪽 자식 노드 접근 : n * 2 + 1

    int Make_SegmentTree(int Node, int Start, int End){

        // 재귀의 종료 조건 : 시작 범위 == 끝 범위일 경우
        if (Start == End) return SegmentTree[Node] = Arr[Start];
        
        int Mid = (Start + End) / 2;
        int Left_Result  = Make_SegmentTree(Node * 2, Start, Mid);
        int Right_Result = Make_SegmentTree(Node * 2 + 1, Mid + 1, End);
        SegmentTree[Node] = Left_Result + Right_Result;
    
        return SegmentTree[Node];
    }
    
    int main(){
        // 세그먼트 트리의 초기 크기를 설정
        int Tree_Height = (int) ceil(log2(N));
        int Tree_Size = (1 << (Tree_Height + 1));
        SegmentTree.resize(Tree_Size);

        Make_SegmentTree(1, 0, N - 1);
    }
    ```

### 세그먼트 트리 탐색하기

- case 분류
    1. 현재 우리가 탐색하려는 범위가 세그먼트 트리에 구해놓은 노드 구간과 완전히 겹치지 않는 경우
    2. 현재 우리가 탐색하려는 범위가 세그먼트 트리에 구해놓은 노드 구간과 완전히 겹치는 경우
    3. 1, 2번 경우를 제외. 즉, 현재 우리가 탐색하려는 범위가 세그먼트 트리에 구해놓은 노드 구간과 일부만 걸쳐있는 경우
- 세그먼트 트리의 규칙이 특정 구간의 합인 경우 세그먼트 트리 탐색하기
    ```cpp
    int Sum(int Node, int Start, int End, int Left, int Right){
        if (Left > End || Right < Start) return 0;
        if (Left <= Start && End <= Right) return SegmentTree[Node];
    
        int Mid = (Start + End) / 2;
        int Left_Result = Sum(Node * 2, Start, Mid, Left, Right);
        int Right_Result = Sum(Node * 2 + 1, Mid + 1, End, Left, Right);
        return Left_Result + Right_Result;
    }
    ```

### 세그먼트 트리 값 변경하기

- case 분류
    1. 바꾸고자 하는 index 값이 현재 우리가 탐색하는 범위 내에 속해 있는 경우
    2. 바꾸고자 하는 index 값이 현재 우리가 탐색하는 범위 외에 속해 있는 경우
- 예시
    ```cpp
    void Update_SegmentTree(int Node, int Start, int End, int Index, int Diff){
        if (Index < Start || Index > End) return;
        SegmentTree[Node] = SegmentTree[Node] + Diff;
    
        if (Start != End) {
            int Mid = (Start + End) / 2;
            Update_SegmentTree(Node * 2, Start, Mid, Index, Diff);
            Update_SegmentTree(Node * 2 + 1, Mid + 1, End, Index, Diff);
        }
    }
    
    int main(void){
        int Index = 1;
        int Value = 5;
        int Diff = Value - Arr[Index];
        Arr[Index] = Value;
        Update_SegmentTree(1, 0, N - 1, Index, Diff);
    }
    ```