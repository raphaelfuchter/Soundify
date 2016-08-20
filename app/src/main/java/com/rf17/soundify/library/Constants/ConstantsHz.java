package com.rf17.soundify.library.Constants;

public enum ConstantsHz {

    a(50),
    b(70),
    c(90),
    d(90),
    e(90),
    f(90),
    g(90),
    h(90),
    i(90),
    j(90),
    k(90),
    l(90),
    m(90),
    n(90),
    o(90),
    p(90),
    q(90),
    r(90),
    s(90),
    t(90),
    u(90),
    v(1),
    w(2),
    x(3),
    y(4),
    z(5),
    A(50),
    B(70),
    C(90),
    D(90),
    E(90),
    F(90),
    G(90),
    H(90),
    I(90),
    J(90),
    K(90),
    L(90),
    M(90),
    N(90),
    O(90),
    P(90),
    Q(90),
    R(90),
    S(90),
    T(90),
    U(90),
    V(1),
    W(2),
    X(3),
    Y(4),
    Z(5),
    NUM_0(1),
    NUM_1(1),
    NUM_2(1),
    NUM_3(1),
    NUM_4(1),
    NUM_5(1),
    NUM_6(1),
    NUM_7(1),
    NUM_8(1),
    NUM_9(1),
    BEGIN(1976),
    END(1568),
    SEPARATOR(988);//used as a separator between repeated digits

    private int hz;

    ConstantsHz(int hz){
        this.hz = hz;
    }

    public int getHz(){
        return this.hz;
    }

}
