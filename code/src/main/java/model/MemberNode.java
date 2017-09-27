package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 */
public class MemberNode extends Node {

    private Member member;

    public MemberNode(Member member) {
        this.member = member;
    }
    public Member getMember() {
        throw new NotImplementedException();
    }

    @Override
    public Boolean remove() {
        throw new NotImplementedException();
    }
}
