# 요세푸스 문제
> n과 k가 자연수이고, k < n 이라고 가정. n명이 동그랗게 모여있을 때 임의의 한 명부터 순서를 세어 k번째 사람을 모임에서 제외한다. 남은 n - 1명에서 다시 당음 사람부터 순서를 세서 k번째 사람을 모임에서 제외한다. 이것을 아무도 남지 않을 때까지 계속해서 반복한다. 이때 모임에서 제외되는 사람의 순서를 (n, k) 요세푸스 순열이라고 하며 마지막으로 제외되는 사람을 구하는 문제를 요세푸스 문제라고 한다.

```cpp
long long joseph (long long n,long long k) {
    if(n == 1LL) return 0LL;
    if(k == 1LL) return n - 1LL;
    if(k > n)  return (joseph(n - 1LL, k) + k) % n;
    long long cnt = n / k;
    long long res = joseph(n - cnt, k);
    res -= n % k;
    if(res < 0LL)  res += n;
    else  res += res / (k - 1LL);
    return res;
}
```
