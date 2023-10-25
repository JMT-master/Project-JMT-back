package com.jmt.controller;

import com.jmt.common.TokenProvidor;
import com.jmt.dto.NotificationDto;
import com.jmt.entity.Notification;
import com.jmt.entity.Member;
import com.jmt.service.EmitterService;
import com.jmt.service.NotificationService;
import com.jmt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
/* 에러 스택
com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.jmt.entity.Notification["member"]->com.jmt.entity.Member$HibernateProxy$g4S2NBtG["hibernateLazyInitializer"])
	at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1300) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.DatabindContext.reportBadDefinition(DatabindContext.java:400) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.impl.UnknownSerializer.failForEmpty(UnknownSerializer.java:46) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.impl.UnknownSerializer.serialize(UnknownSerializer.java:29) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:728) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:728) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ObjectWriter$Prefetch.serialize(ObjectWriter.java:1518) ~[jackson-databind-2.13.5.jar:2.13.5]
	at com.fasterxml.jackson.databind.ObjectWriter.writeValue(ObjectWriter.java:1007) ~[jackson-databind-2.13.5.jar:2.13.5]
	at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.writeInternal(AbstractJackson2HttpMessageConverter.java:456) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.http.converter.AbstractGenericHttpMessageConverter.writeInternal(AbstractGenericHttpMessageConverter.java:113) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.http.converter.AbstractHttpMessageConverter.write(AbstractHttpMessageConverter.java:227) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler$HttpMessageConvertingHandler.sendInternal(ResponseBodyEmitterReturnValueHandler.java:212) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler$HttpMessageConvertingHandler.send(ResponseBodyEmitterReturnValueHandler.java:205) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.sendInternal(ResponseBodyEmitter.java:204) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.initialize(ResponseBodyEmitter.java:132) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler.handleReturnValue(ResponseBodyEmitterReturnValueHandler.java:185) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.handleReturnValue(HandlerMethodReturnValueHandlerComposite.java:78) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:135) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1072) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:965) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:529) ~[tomcat-embed-core-9.0.80.jar:4.0.FR]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.3.30.jar:5.3.30]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:623) ~[tomcat-embed-core-9.0.80.jar:4.0.FR]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:209) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111) ~[spring-web-5.3.30.jar:5.3.30]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:337) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:122) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:116) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:109) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at com.jmt.common.JwtAuthenticationFilter.doFilterInternal(JwtAuthenticationFilter.java:37) ~[classes/:na]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:112) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:82) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:346) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:221) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:186) ~[spring-security-web-5.7.11.jar:5.7.11]
	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:354) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:267) ~[spring-web-5.3.30.jar:5.3.30]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.3.30.jar:5.3.30]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.30.jar:5.3.30]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:168) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:481) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:130) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:390) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:926) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1790) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.80.jar:9.0.80]
	at java.base/java.lang.Thread.run(Thread.java:834) ~[na:na]

 */
@RestController
@Slf4j
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    private final MemberService memberService;

    private final TokenProvidor tokenProvidor;

    private final EmitterService emitterService;



    @GetMapping(value = "/sub",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal String userid){
        log.debug("이미터 유저 확인 : " + userid);
        SseEmitter checkEmitter= emitterService.subscribe(userid);
        log.debug("emitter sub chekc : " + checkEmitter);
        return checkEmitter;
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationDto> sendData(
            @AuthenticationPrincipal String userid,
            @RequestBody NotificationDto dto) {
        Notification notification = NotificationDto.toEntity(dto);
        log.debug("MemberTest Userid : " + userid);
        Member member = memberService.getMember(userid);
        notification.setMember(member);
        notificationService.addNotification(notification);

        emitterService.send(userid,dto);

        NotificationDto notificationDto = NotificationDto.toDto(notification);
        return ResponseEntity.ok().body(notificationDto);
    }

    //Todo : 등록시 알람 갱신되는지 확인하기
    @PutMapping
    public ResponseEntity<NotificationDto> regNotification(@RequestBody NotificationDto dto){
        Notification entity = NotificationDto.toEntity(dto);
//        entity.setMember(memberService.getMember(tokenProvidor.getUserId(token)));
        log.info("entity : " + entity);
        NotificationDto alarmDto = NotificationDto.toDto(entity);
        if(entity == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        try {
            notificationService.addNotification(entity);
            return ResponseEntity.ok().body(alarmDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(alarmDto);
        }
    }

    @PostMapping
    public ResponseEntity<List<NotificationDto>> requestNotification(@AuthenticationPrincipal String userid){
        log.debug("===============알람 userid : " + userid);
        List<Notification> entities = notificationService.showNotification(userid);
        log.debug("===============entities : " + entities);
        List<NotificationDto> entity = new ArrayList<>();

        for(Notification notification : entities){
            entity.add(NotificationDto.toDto(notification));
        }

        System.out.println("entity = " + entity);

        System.out.println("ResponseEntity.ok().body(entity); = " + ResponseEntity.ok().body(entity));
        if(entities == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        try {
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<NotificationDto>> removeNotification(String alarmId , @AuthenticationPrincipal String userid){
        String tempNotificationId = "8a8ab7938b3cbcaf018b3cbd84990000";
        Member member = memberService.getMember("이장");
        notificationService.deleteNotification(tempNotificationId);
        List<Notification> entities = notificationService.showNotification(userid);

        List<NotificationDto> entity = new ArrayList<>();
        for(Notification notification : entities){
            entity.add(NotificationDto.toDto(notification));
        }
        try {
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }
}
