<%@include file="/WEB-INF/p/shared/taglibs.jsp"%>

<h1>
Welcome to voms-admin registration for the <span class="voName"> ${voName}</span> VO.
</h1>

<p>
To access the VO resources, you must agree to the VO's Acceptable Usage Policy (AUP) rules.
<br/>
Please fill out all fields in the form below and click on the submit
button at the bottom of the page.
</p>
<p>
After you submit this request, you will receive an email with instructions on how to proceed. 
<br/>
<span style="font-weight: bold">
Your request will not be forwarded to the VO managers until you confirm that you have a valid email 
address by following those instructions.</span>
</p>

<p><span style="font-weight: bold">IMPORTANT</span>:</p>
<p>
By submitting this information you agree that it may be distributed to and stored by 
VO and site administrators. You also agree that action may be taken to confirm the information you provide 
is correct, that it may be used for the purpose of controlling access to VO resources and that it may be 
used to contact you in relation to this activity.
</p>

<s:form action="submit-request" validate="true">
  <h2 style="color: black">Your distinguished name (DN):</h2>
  <div class="highlight">
      <s:property value="requester.certificateSubject"/>
  </div>
  <h2 style="color: black">Your CA:</h2>
  <div class="regDN">
      <s:property value="requester.certificateIssuer"/>
  </div>
  <ul class="form">
    <li>
      <s:textfield name="name" label="%{'Your name'}" size="40"/>
    </li>
    <li>
      <s:textfield name="surname" label="%{'Your surname'}" size="40"/>
    </li>
    <li>
      <s:textfield name="institution" label="%{'Your institution'}" size="40"/>
    </li>
    <li>
      <s:textfield name="phoneNumber" label="%{'Your phoneNumber'}" size="40"/>
    </li>
    <li>
      <s:textarea name="address" label="%{'Your address'}" rows="5" cols="40"/>
    </li>
    <li>
      <s:textfield name="emailAddress" value="%{requester.emailAddress}" size="60" label="%{'Your email address'}"/>
    </li>
    <li>
     <h2 style="color: black">The VO AUP:</h2>
      <s:textarea rows="20" cols="80" value="%{currentAUPVersion.URLContent}" readonly="true"/>
    </li>
    <li class="aupAcceptance">
      <s:checkbox name="aupAccepted" label="I confirm I have read and agree with the terms expressed in the VO Acceptable Usage Policy
      document displayed above." labelposition="right"/>
    </li>
    <li>
     <s:submit/>
    </li>
  </ul>     
</s:form>