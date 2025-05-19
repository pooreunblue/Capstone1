#!/bin/bash
echo 'JAVA_TOOL_OPTIONS="-Djava.net.preferIPv4Stack=true"' > /etc/profile.d/java-options.sh
chmod +x /etc/profile.d/java-options.sh
