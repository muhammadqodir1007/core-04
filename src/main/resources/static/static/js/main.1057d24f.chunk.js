(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{24:function(e,t,n){},29:function(e,t,n){e.exports=n.p+"static/media/logo.15149f3d.svg"},38:function(e,t,n){e.exports=n.p+"static/media/eye.28b54d3c.svg"},42:function(e,t,n){e.exports=n(59)},59:function(e,t,n){"use strict";n.r(t);var a,r,c,o,l,i,s=n(0),m=n.n(s),u=n(34),p=n.n(u),d=(n(50),function(e){e&&e instanceof Function&&n.e(3).then(n.bind(null,62)).then(function(t){var n=t.getCLS,a=t.getFID,r=t.getFCP,c=t.getLCP,o=t.getTTFB;n(e),a(e),r(e),c(e),o(e)})}),b=n(9),h=n(7),f=Object(h.b)(Object(h.c)(a||(a=Object(b.a)(["\n\n    body {\n       font-family: 'Source Sans Pro', sans-serif;\n     \n  }\n  \n  .center {\n    display: flex;\n    justify-content: center;\n    align-items: center;\n    flex-direction: column;\n    \n     display: flex;\n       flex-direction: column;\n       align-items: flex-start;\n       padding: 40px 35px;      \n \n       width: 340px;\n    \n       left: 550px;\n       top: 213px;\n       background: #FFFFFF;\n       box-shadow: 0px 3px 12px rgba(29, 30, 38, 0.1), 0px 6px 18px rgba(29, 30, 38, 0.1);\n  }\n"])))),g=function(e){var t=e.children;return m.a.createElement(h.a,{theme:{mode:"light"}},m.a.createElement(f,null),t)},E=n(8),x=n(2),O=n(4),v=n(61),j=(n(24),n(60)),w=n(13),y=n(38),C=n.n(y),k=h.d.div(r||(r=Object(b.a)(["\n  --error: #E54322;\n\n  label {\n    width: 100%;\n    font-weight: 400;\n    font-size: 14px;\n    line-height: 18px;\n    color: #474A59;\n\n    .required {\n      color: red;\n    }\n  }\n\n  .form-input {\n    ","\n    height: 42px;\n    background: #FFFFFF;\n    border: 1px solid ",";\n    display: flex;\n    justify-content: space-between;\n    align-items: center;\n    padding: 9px 6px 9px 12px;\n    transition: 0.2s;\n    position: relative;\n\n    .eye-icon {\n      cursor: pointer;\n    }\n\n    ",'\n    input {\n      border: none;\n      outline: none;\n      background: none;\n      font-weight: 400;\n      font-size: 16px;\n      line-height: 24px;\n      color: #6C6F80;\n\n      &[type="password"] {\n        letter-spacing: 0.99px;\n        color: #FFFFFF;\n        caret-color: #000;\n      }\n\n    }\n\n  }\n\n  .error-message {\n    font-weight: 400;\n    font-size: 12px;\n    line-height: 18px;\n    color: ',";\n    margin-top: 4px;\n\n  }\n\n  ","\n  ","\n\n"])),function(e){return e.labelText&&Object(h.c)(c||(c=Object(b.a)(["\n      margin-top: 6px;\n    "])))},function(e){var t=e.isFocused,n=e.isError;return t?"#008ACE":n?"var(--error)":"#CED0DB"},function(e){var t=e.star,n=e.type,a=e.isOpen;return"password"===n&&!a&&Object(h.c)(o||(o=Object(b.a)(['\n      &:after {\n        content: "','";\n        position: absolute;\n        top: 12.5px;\n        left: 13.5px;\n        font-weight: 400;\n        font-size: 14px;\n        line-height: 24px;\n        color: #6C6F80;\n\n      }\n    '])),t)},function(e){return e.isError?"var(--error)":"#6C6F80"},function(e){var t=e.mb;return t&&Object(h.c)(l||(l=Object(b.a)(["\n    margin-bottom: ","px;\n  "])),t)},function(e){var t=e.mt;return t&&Object(h.c)(i||(i=Object(b.a)(["\n    margin-top: ","px;\n  "])),t)});function F(e){for(var t=String(e).length,n="",a=0;t>a;a++)n+="*";return n}var N,T,S,P,A,q,B=function(e){var t=e.type,n=void 0===t?"text":t,a=e.onChange,r=void 0===a?function(){return""}:a,c=e.labelText,o=void 0===c?"":c,l=e.required,i=e.placeholder,u=void 0===i?"":i,p=e.mt,d=void 0===p?"":p,b=e.mb,h=void 0===b?"":b,f=e.errorMessage,g=void 0===f?"":f,E=e.isError,x=void 0!==E&&E,v=Object(s.useState)({isFocused:!1,isOpen:!1}),j=Object(O.a)(v,2),y=j[0],N=j[1],T=Object(s.useState)(""),S=Object(O.a)(T,2),P=S[0],A=S[1],q=Object(s.useCallback)(function(){return N(function(e){return Object(w.a)({},e,{isOpen:!e.isOpen})})},[]),B=Object(s.useCallback)(function(e){A(e.target.value),r(e.target.value)},[r]),I=Object(s.useCallback)(function(){return N(function(e){return Object(w.a)({},e,{isFocused:!0})})},[]),z=Object(s.useCallback)(function(){return N(function(e){return Object(w.a)({},e,{isFocused:!1})})},[]);return m.a.createElement(m.a.Fragment,null,m.a.createElement(k,{isOpen:y.isOpen,isFocused:y.isFocused,star:F(P),type:n,labelText:o,mt:d,mb:h,isError:x},m.a.createElement("label",null,o," ",l&&m.a.createElement("span",{className:"required"},"*"),m.a.createElement("div",{className:"form-input"},m.a.createElement("input",{type:y.isOpen?"text":n,onChange:B,onFocus:I,onBlur:z,placeholder:u}),"password"===n&&m.a.createElement("img",{src:C.a,className:"eye-icon",alt:"eye-img",onClick:q}))),g&&m.a.createElement("p",{className:"error-message"},g)))},I=h.d.button(N||(N=Object(b.a)(["\n  background: #008ACE;\n  border-radius: 3px;\n  height: 42px;\n  font-weight: 600;\n  font-size: 16px;\n  line-height: 24px;\n  color: #FFFFFF;\n  border: none;\n  outline: none;\n  width: 100%;\n  display: flex;\n  justify-content: center;\n  align-items: center;\n  transition: 0.3s;\n\n  &:active {\n    transform: scale(0.9);\n  }\n\n  ","\n  ","\n"])),function(e){var t=e.mb;return t&&Object(h.c)(T||(T=Object(b.a)(["\n    margin-bottom: ","px;\n  "])),t)},function(e){var t=e.mt;return t&&Object(h.c)(S||(S=Object(b.a)(["\n    margin-top: ","px;\n  "])),t)}),z=function(e){var t=e.type,n=void 0===t?"button":t,a=e.children,r=e.disabled,c=e.onClick,o=void 0===c?function(){return""}:c,l=e.mb,i=e.mt;return m.a.createElement(I,{type:n,onClick:function(){return!r&&o()},mb:l,mt:i},a)},M=function(){var e=Object(s.useState)(),t=Object(O.a)(e,2),n=t[0],a=t[1],r=Object(s.useState)(),c=Object(O.a)(r,2),o=c[0],l=c[1],i=Object(s.useState)(),u=Object(O.a)(i,2),p=u[0],d=u[1],b=Object(s.useState)(!1),h=Object(O.a)(b,2),f=h[0],g=h[1];return m.a.createElement(v.a,{className:"container center",style:{marginTop:"40px"}},m.a.createElement("h4",{className:"h4"},"Sign up"),m.a.createElement("div",{className:"",style:{width:"270px"}},m.a.createElement(B,{type:"email",labelText:"E-mail",required:!0,onChange:function(e){return a(e)},placeholder:"Email",mt:9,mb:16}),m.a.createElement(B,{type:"password",labelText:"Password",required:!0,placeholder:"Password",mt:4,mb:4,onChange:function(e){return l(e)},errorMessage:o<8?"Your password must be at least 8 characters":"",isError:f}),m.a.createElement("label",{className:"label"},"Your password must be at least 8 characters "),m.a.createElement(B,{type:"password",labelText:"Repeat password",required:!0,placeholder:"Password",mt:6,mb:32,onChange:function(e){return d(e)},errorMessage:f?"Passwords don't match":"",isError:f}),m.a.createElement(z,{mt:"32",mb:"16",onClick:function(e){g(!1),j.a.post("http://localhost:8081/api/v1/auth/sign-up",{email:n,password:o,prePassword:p}).then(function(e){e.data.data.errorEmail||e.data.data.errorPassword||(console.log(e),window.location.replace("/verify-email/"+n))}).catch(function(e){console.log(e)})}},"Sign Up"),m.a.createElement("p",{className:"p-2"},"Already a user?"," ",m.a.createElement(E.b,{to:"/sign-in"},"Log in."))))},L=function(){var e=Object(s.useState)(),t=Object(O.a)(e,2),n=t[0],a=t[1],r=Object(s.useState)(),c=Object(O.a)(r,2),o=c[0],l=c[1],i=Object(x.l)();Object(s.useEffect)(function(){localStorage.getItem("AccessToken")&&j.a.get("http://localhost:8081/api/v1/user/me",{headers:{Authorization:localStorage.getItem("AccessToken")}}).then(function(e){i("/cabinet")}).catch(function(e){return console.log(e)})},[]);return m.a.createElement(v.a,{className:"container center",style:{marginTop:"62px"}},m.a.createElement("h4",{className:"h4"},"Log in"),m.a.createElement("div",{className:"",style:{width:"270px"}},m.a.createElement(B,{type:"email",labelText:"E-mail",required:!0,onChange:function(e){return a(e)},placeholder:"Email",mt:9,mb:16}),m.a.createElement(B,{type:"password",labelText:"Password",required:!0,placeholder:"Password",mt:4,mb:4,onChange:function(e){return l(e)}}),m.a.createElement(E.b,{className:"label-1",to:"/forgot-password"},"Forgot password? "),m.a.createElement(z,{mt:"32",mb:"16",onClick:function(){j.a.post("http://localhost:8081/api/v1/auth/sign-in",{email:n,password:o}).then(function(e){localStorage.setItem("AccessToken",e.data.data.tokenType+e.data.data.accessToken),localStorage.setItem("RefreshToken",e.data.data.refreshToken),window.location.replace("/cabinet")}).catch(function(e){return console.log(e)})}},"Log in"),m.a.createElement("p",{className:"p-2"},"Need an account?"," ",m.a.createElement(E.b,{to:"/sign-up"},"Sign up."))))},R=n(29),Y=n.n(R),D=function(){var e=Object(x.n)();return m.a.createElement(v.a,{className:"container center",style:{marginTop:"40px"}},m.a.createElement("img",{src:Y.a,className:"logo-icon",alt:"logo-img"}),m.a.createElement("h2",{className:"verify-title"},"Verify your email"),m.a.createElement("h5",{className:"verify-text"},"We have sent verification link to your e-mail ",e.email,". Please verify your e-mail address to continue using CloudGantt."),m.a.createElement("div",{className:"link-wrapper"},m.a.createElement(E.b,{className:"click-link",onClick:function(){j.a.get("http://localhost:8081/api/v1/auth/resend-verification-code?email="+e.email).then(function(e){return alert(e.data.data)}).catch(function(e){return console.log(e)})}},"Click here"),m.a.createElement("p",{className:"p-3"},"if you didn't receive an e-mail ")))},J=function(){var e=Object(x.n)();return Object(s.useEffect)(function(){j.a.get("http://localhost:8081/api/v1/auth/confirm-email/"+e.verificationCode).then(function(e){window.location.replace("/sign-in")}).catch(function(e){console.log(e.response.data)})},[]),m.a.createElement(m.a.Fragment,null,m.a.createElement("h2",null,"Verifying your email"))},V=function(){var e=Object(x.l)();return Object(s.useEffect)(function(){localStorage.getItem("AccessToken")||e("/sign-in")},[]),m.a.createElement(m.a.Fragment,null,m.a.createElement(v.a,{className:"center"},m.a.createElement("h4",{className:"h4"},"CABINET")))},W=h.d.button(P||(P=Object(b.a)(["\n  border: 2px solid #008ACE;\n  background: #FFFFFF;\n  border-radius: 3px;\n  height: 42px;\n  font-weight: 600;\n  font-size: 16px;\n  line-height: 24px;\n  color: #008ACE;\n  outline: none;\n  width: 100%;\n  display: flex;\n  justify-content: center;\n  align-items: center;\n  transition: 0.3s;\n\n  &:active {\n    transform: scale(0.9);\n  }\n\n  ","\n  ","\n"])),function(e){var t=e.mb;return t&&Object(h.c)(A||(A=Object(b.a)(["\n    margin-bottom: ","px;\n  "])),t)},function(e){var t=e.mt;return t&&Object(h.c)(q||(q=Object(b.a)(["\n    margin-top: ","px;\n  "])),t)}),G=function(e){var t=e.type,n=void 0===t?"button":t,a=e.children,r=e.disabled,c=e.onClick,o=void 0===c?function(){return""}:c,l=e.mb,i=e.mt;return m.a.createElement(W,{type:n,onClick:function(){return!r&&o()},mb:l,mt:i},a)},U=function(){var e=Object(s.useState)(),t=Object(O.a)(e,2),n=t[0],a=t[1],r=Object(s.useState)(),c=Object(O.a)(r,2),o=c[0],l=c[1],i=Object(s.useState)(!1),u=Object(O.a)(i,2),p=u[0],d=u[1],b=Object(s.useState)(!1),h=Object(O.a)(b,2),f=h[0],g=h[1],E=Object(x.n)(),w=Object(x.l)();return m.a.createElement(v.a,{className:"container center",style:{marginTop:"40px"}},m.a.createElement("h4",{className:"h4"},"Change password"),m.a.createElement("div",{className:"",style:{width:"270px"}},m.a.createElement(B,{type:"password",labelText:"Enter new password",required:!0,placeholder:"Password",mt:4,mb:4,onChange:function(e){return a(e)},errorMessage:f?"Your password must be at least 8 characters":"",isError:f}),m.a.createElement("label",{className:"label"},"Your password must be at least 8 characters "),m.a.createElement(B,{type:"password",labelText:"Repeat new password",required:!0,placeholder:"Password",mt:4,mb:4,onChange:function(e){return l(e)},errorMessage:p?"Passwords don't match":"",isError:p}),m.a.createElement("label",{className:"label"},"Your password must be the same "),m.a.createElement(z,{mt:"38",mb:"16",onClick:function(){n<8?g(!0):n===o?(d(!1),j.a.post("http://localhost:8081/api/v1/auth/reset-password",{password:n,prePassword:o,email:E.email,verificationCode:E.verificationCode}).then(function(e){w("/sign-in")}).catch(function(e){console.log(e),alert("error")})):d(!0)}},"Change password"),m.a.createElement(G,{onClick:function(){w("/sign-in")}},"Back")))},H=function(){var e=Object(s.useState)(),t=Object(O.a)(e,2),n=t[0],a=t[1],r=Object(s.useState)(!1),c=Object(O.a)(r,2),o=c[0],l=c[1],i=Object(s.useState)(""),u=Object(O.a)(i,2),p=u[0],d=u[1],b=Object(x.l)(),h=Object(s.useCallback)(function(e){o&&(d(""),l(!1)),a(e)},[o]);return m.a.createElement(v.a,{className:"container center",style:{marginTop:"40px"}},m.a.createElement("h4",{className:"h4"},"Forgot password?"),m.a.createElement("p",{className:"instructions"},"Enter the email address and we'll send you reset instructions."),m.a.createElement("div",{className:"",style:{width:"270px"}},m.a.createElement(B,{type:"email",labelText:"E-mail",required:!0,placeholder:"Email",mt:4,mb:4,onChange:h,isError:o,errorMessage:p}),m.a.createElement(z,{mt:"32",mb:"16",onClick:function(e){null!==n&&j.a.get("http://localhost:8081/api/v1/auth/forgot-password?email="+n).then(function(e){e.data.data.errorEmail?(d(e.data.data.message),l(e.data.data.errorEmail)):b("/check-email/"+n)}).catch(function(e){console.log(e)})}},"Reset password"),m.a.createElement(G,{onClick:function(){b("/sign-in")}},"Back")))},K=function(){var e=Object(x.n)(),t=Object(x.l)();return m.a.createElement(v.a,{className:"container center",style:{marginTop:"40px"}},m.a.createElement("img",{src:Y.a,className:"logo-icon",alt:"logo-img"}),m.a.createElement("h2",{className:"verify-title"},"Check your email"),m.a.createElement("h5",{className:"check-mail",style:{marginTop:"9px"}},"We have sent password reset instructions to: ",e.email),m.a.createElement("h5",{className:"check-mail",style:{marginTop:"10px",marginBottom:"49px"}},"If it doesn't arrive soon, check your spam folder or",m.a.createElement(E.b,{className:"click-link",onClick:function(){null!==e.email&&j.a.get("http://localhost:8081/api/v1/auth/forgot-password?email="+e.email).then(function(e){return alert("Success sent")}).catch(function(e){console.log(e)})}}," send the email again")),m.a.createElement(G,{onClick:function(){t("/sign-in")}},"Back to log in"))},Q=function(){return m.a.createElement(E.a,null,m.a.createElement(x.c,null,m.a.createElement(x.a,{path:"/sign-up",element:m.a.createElement(M,null)}),m.a.createElement(x.a,{path:"/sign-in",exact:!0,element:m.a.createElement(L,null)}),m.a.createElement(x.a,{path:"/verify-email/:email",element:m.a.createElement(D,null)}),m.a.createElement(x.a,{path:"/confirm-email/:verificationCode",element:m.a.createElement(J,null)}),m.a.createElement(x.a,{path:"/change-password/:email/:verificationCode",element:m.a.createElement(U,null)}),m.a.createElement(x.a,{path:"/forgot-password",element:m.a.createElement(H,null)}),m.a.createElement(x.a,{path:"/check-email/:email",element:m.a.createElement(K,null)}),m.a.createElement(x.a,{path:"/cabinet",element:m.a.createElement(V,null)}),m.a.createElement(x.a,{path:"/",exact:!0,element:m.a.createElement(L,null)})))};p.a.createRoot(document.getElementById("root")).render(m.a.createElement(g,null,m.a.createElement(Q,null))),d()}},[[42,1,2]]]);
//# sourceMappingURL=main.1057d24f.chunk.js.map