package com.example.trip_itinerary;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithMemberSecurityFactory implements WithSecurityContextFactory<WithMember> {
    @Override
    public SecurityContext createSecurityContext(WithMember annotation) {
        String username = annotation.username();
        String password = annotation.password();

        Member tester = Member.of("tester", username, password);
        MemberAdapter principal = new MemberAdapter(tester);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return context;
    }
}
