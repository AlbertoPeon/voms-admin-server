/**
 * Copyright (c) Members of the EGEE Collaboration. 2006-2009.
 * See http://www.eu-egee.org/partners/ for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors:
 * 	Andrea Ceccanti (INFN)
 */
package org.glite.security.voms.admin.servlets;

import java.math.BigInteger;
import java.security.cert.X509Certificate;

import javax.servlet.ServletRequest;

import org.glite.security.voms.admin.core.VOMSServiceConstants;
import org.glite.security.voms.admin.error.VOMSException;
import org.italiangrid.utils.voms.SecurityContextImpl;
import org.italiangrid.utils.voms.VOMSSecurityContext;
import org.italiangrid.voms.ac.VOMSACValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitSecurityContext {

	protected static Logger log = LoggerFactory
			.getLogger(InitSecurityContext.class);

	
	public static void setContextFromRequest(final ServletRequest req,
		final VOMSACValidator validator) {

		log.debug("Creating a new security context");
		VOMSSecurityContext sc = new VOMSSecurityContext();
	
		String remote = req.getRemoteAddr();
		sc.setRemoteAddr(remote);

		X509Certificate[] cert = null;
		try {
			// Interpret the client's certificate.
			cert = (X509Certificate[]) req
					.getAttribute("javax.servlet.request.X509Certificate");
		} catch (Throwable t) {
			throw new VOMSException(t.getMessage(), t);
		}

		if (cert == null) {
			sc.setClientName(VOMSServiceConstants.UNAUTHENTICATED_CLIENT);
			sc.setIssuerName(VOMSServiceConstants.VIRTUAL_CA);
		} else {
			// Client certificate found.
			sc.setClientCertChain(cert);

			// Do not allow internal credentials coming from an external source.
			if (sc.getClientName() != null
					&& sc.getClientName().startsWith(
							VOMSServiceConstants.INTERNAL_DN_PREFIX)) {
				log.error("Client name starts with internal prefix, discarding credentials: "
						+ sc.getClientName());
				sc.setClientName(VOMSServiceConstants.UNAUTHENTICATED_CLIENT);
				sc.setIssuerName(VOMSServiceConstants.VIRTUAL_CA);
			} else if (sc.getIssuerName() != null
					&& sc.getIssuerName().startsWith(
							VOMSServiceConstants.INTERNAL_DN_PREFIX)) {
				log.error("Client issuer starts with internal prefix, discarding credentials: "
						+ sc.getClientName());
				sc.setClientName(VOMSServiceConstants.UNAUTHENTICATED_CLIENT);
				sc.setIssuerName(VOMSServiceConstants.VIRTUAL_CA);
			}
		}

		VOMSSecurityContext.setCurrentContext(sc);
	}

	public static void logConnection() {

		VOMSSecurityContext sc = VOMSSecurityContext.getCurrentContext();

		if (sc.getClientCert() == null) {

			log.info("Unauthenticated connection from \"{}\"",
					sc.getRemoteAddr());

		} else {

			String clientName = sc.getClientName();
			String issuerName = sc.getIssuerName();

			BigInteger sn = sc.getClientCert().getSerialNumber();

			String serialNumber = (sn == null) ? "NULL" : sn.toString();

			log.info("Connection from \"" + sc.getRemoteAddr() + "\" by "
					+ clientName + " (issued by \"" + issuerName + "\", "
					+ "serial " + serialNumber + ")");
		}
	}

	/**
	 * Initialize a clear security context, which will fail on all security
	 * checks. It is intended for non-authenticated requests.
	 */
	public static void setClearContext() {

		log.info("Clearing the security context");
		VOMSSecurityContext sc = new VOMSSecurityContext();
		VOMSSecurityContext.setCurrentContext(sc);

		sc.setClientName(VOMSServiceConstants.UNAUTHENTICATED_CLIENT);
		sc.setIssuerName(VOMSServiceConstants.VIRTUAL_CA);
	}
}
