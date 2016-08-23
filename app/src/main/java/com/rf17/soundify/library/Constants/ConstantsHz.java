package com.rf17.soundify.library.Constants;

public enum ConstantsHz {

    a('a', 1000),
    b('b', 1100),
    c('c', 1200),
    d('d', 1300),
    e('e', 1400),
    f('f', 1500),
    g('g', 1600),
    h('h', 1700),
    i('i', 1800),
    j('j', 1900),
    k('k', 2000),
    l('l', 2100),
    m('m', 2200),
    n('n', 2300),
    o('o', 2400),
    p('p', 2500),
    q('q', 2600),
    r('r', 2700),
    s('s', 2800),
    t('t', 2900),
    u('u', 3000),
    v('v', 3100),
    w('w', 3200),
    x('x', 3300),
    y('y', 3400),
    z('z', 3500),
    A('A', 50),
    B('B', 70),
    C('C', 90),
    D('D', 90),
    E('E', 90),
    F('F', 90),
    G('G', 90),
    H('H', 90),
    I('I', 90),
    J('J', 90),
    K('K', 90),
    L('L', 90),
    M('M', 90),
    N('N', 90),
    O('O', 90),
    P('P', 90),
    Q('Q', 90),
    R('R', 90),
    S('S', 90),
    T('T', 90),
    U('U', 90),
    V('V', 1),
    W('W', 2),
    X('X', 3),
    Y('Y', 4),
    Z('Z', 5),
    NUM_0('0', 1),
    NUM_1('1', 1),
    NUM_2('2', 1),
    NUM_3('3', 1),
    NUM_4('4', 1),
    NUM_5('5', 1),
    NUM_6('6', 1),
    NUM_7('7', 1),
    NUM_8('8', 1),
    NUM_9('9', 1),
    BEGIN('<', 4000),
    END('>', 5000),
    TX_ERROR('@', 50),
    SEPARATOR('|', 988); // used as a separator between repeated digits

    private char desc;
    private int hz;

    ConstantsHz(char desc, int hz) {
        this.desc = desc;
        this.hz = hz;
    }

    public char getDesc() {
        return this.desc;
    }

    public int getHz() {
        return this.hz;
    }

}
