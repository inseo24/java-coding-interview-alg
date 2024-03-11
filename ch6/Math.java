public class Math {



    // 6.1 무거운 알약: 20개의 약병이 있는데 19개는 1.0그램 알약이, 1개는 1.1그램 알약이 들어있다.
    // 정확한 저울 하나만 사용해서 1.1그램 알약을 찾아라. 저울은 딱 한 번만 쓸 수 있다.

    // 약병 1에서 알약 1개를 꺼내고, 약병 2이세서 2개를 꺼내고, 약병 3에서 3개를 꺼내고, ... 약병 20에서 20개를 꺼내서 총 210그램이 나온다.
    // (1 + 2 + 3 + ... + 20) = 210
    // (전체 무게 - 210) / 0.1 = [1.1그램 알약의 위치]
    // ex) 211.3 - 210 = 1.3 / 0.1 = 13


    // 6.2 농구: 농구 골대가 하나 있는데 다음 두 게임 중 하나를 해볼 수 있다.
    // 게임1: 슛을 한 번 쏴서 골대에 넣어야 한다.
    // 게임2: 슛을 세 번 쏴서 두 번 골대에 넣어야 한다.
    // 슛을 넣을 확률이 p라고 할 때, p가 어떤 값일 때 첫 번째 게임을, p가 어떤 값일 때 두 번째 게임을 선택해야 할까?

    // 게임1에서 이길 확률: p
    // 게임2에서 이길 확률: 3(1-p)p^2 + p^3 = 3p^2 - 2p^3
    // 3번 중 2번 골을 넣을 확률과 3번 모두 골을 넣을 확률의 합
    //   - 세번 골을 넣을 확률: p^3
    //   - 두번 골을 넣을 확률: 3(1-p)p^2

    // p(게임1) > P(게임2)일 때 게임1을 선택한다.
    // p       > 3p^2 - 2p^3
    // 1 > 3p - 2p^2
    // 2p^2 - 3p + 1 > 0
    // (2p - 1)(p - 1) > 0
    // p > 1 or p < 0.5
    // 0 < p < 0.5 -> 게임 1 선택
    // 0.5 < p < 1 -> 게임 2 선택
    // 만약 p = 0, 0.5, 1이면 아무거나 선택해도 상관없다.

    // 6.3 도미노: 8x8 체스판에서 대각선 반대 방향 끝에 있는 셀 2개가 떨어져나갔다.
    // 하나의 도미노로 정확히 2개의 정사각형을 덮을 수 있을 때, 31개의 도미노로 보드 전체를 덮을 수 있겠는가?
    // **도미노: 2x1 크기의 타일, 1x1 크기의 정사각형 2개로 이루어져 있다.

    // 할 수 없다. 31개의 도미노로 덮을 수 있는 셀의 색은 31개의 흰색 셀과 31개의 검은색 셀로 이루어져야 한다.
    // 왜냐면 도미노 하나는 흰색 셀과 검은색 셀 2개를 덮기 때문이다.
    // 하지만 대각선 방향에 있는 셀의 색은 같기 때문에 31개의 도미노로 덮을 수 없다.


    // 6.4 삼각형 위의 개미: 개미 3마리가 삼각형의 각 꼭지점에 있다. 개미 3마리가 삼각형 모서리를 따라 걷기 시작했을 때,
    // 두 마리 혹은 세 마리 전부가 충돌할 확률은 얼마인가?
    // 각 개미는 자신이 움직일 방향을 임의로 선택할 수 있는데, 같은 확률로 두 방향 중 하나를 선택한다.
    // 또한 그들은 같은 속도로 걷는다. 이 문제를 확장해서 n개의 개미가 n각형 위에 있을 때 충돌할 확률을 구하라.

    // 두 마리가 충돌하지 않으려면, 아래 2가지 경우이거나 움직이지 않아야 한다.
    // 1. 모든 개미가 시계방향으로 움직임
    // 2. 모든 개미가 반시계방향으로 움직임

    // P(시계방향) = (1/2)^3 = 1/8
    // P(반시계방향) = (1/2)^3 = 1/8
    // P(같은 방향) = P(시계방향) + P(반시계방향) = (1/2)^3 + (1/2)^3 = 1/4

    // 충돌할 확률은 개미가 같은 방향으로 움직일 확률의 여집합이 된다.
    // P(충돌) = 1 - P(같은 방향) = 1 - 1/4 = 3/4

    // n각형으로 일반화
    // P(시계방향) = (1/2)^n
    // P(반시계방향) = (1/2)^n
    // P(같은 방향) = (1/2)^n + (1/2)^n = (1/2)^(n-1)
    // P(충돌) = 1 - P(같은 방향) = 1 - (1/2)^(n-1)

}
