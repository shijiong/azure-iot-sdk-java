// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.device;

import mockit.Deencapsulation;
import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for Iothub connection string.
 */
public class IotHubConnectionStringTest 
{
    private static final String VALID_HUBNAME = "iothub";
    private static final String VALID_HOSTNAME = VALID_HUBNAME + ".device.com";
    private static final String VALID_DEVICEID = "testdevice";
    private static final String VALID_SHARED_ACCESS_KEY = "key+adjkl234j52=";
    private static final String VALID_SHARED_ACCESS_TOKEN = "SharedAccessSignature sr=sample-iothub-hostname.net%2fdevices%2fsample-device-ID&sig=S3%2flPidfBF48B7%2fOFAxMOYH8rpOneq68nu61D%2fBP6fo%3d&se=1469813873";
    private static final String IOTHUB_CONNECTION_STRING_CLASS = "com.microsoft.azure.sdk.iot.device.IotHubConnectionString";

    private void assertConnectionString(Object iotHubConnectionString, String expectedHostName,
                                        String expectedDeviceId, String expectedSharedAccessKey, String expectedSharedAccessToken)
    {
        assertNotNull(iotHubConnectionString);

        String hostName = Deencapsulation.getField(iotHubConnectionString, "hostName");
        String hubName = Deencapsulation.getField(iotHubConnectionString, "hubName");
        String deviceId = Deencapsulation.getField(iotHubConnectionString, "deviceId");
        String sharedAccessKey = Deencapsulation.getField(iotHubConnectionString, "sharedAccessKey");
        String sharedAccessToken = Deencapsulation.getField(iotHubConnectionString, "sharedAccessToken");

        int iotHubNameEndIdx = expectedHostName.indexOf(".");
        String expectedHubName = expectedHostName.substring(0, iotHubNameEndIdx);

        assertEquals(expectedHostName, hostName);
        assertEquals(expectedHubName, hubName);
        assertEquals(expectedDeviceId, deviceId);
        assertEquals(expectedSharedAccessKey, sharedAccessKey);
        assertEquals(expectedSharedAccessToken, sharedAccessToken);

    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_010: [The constructor shall interpret the connection string as a set of key-value pairs delimited by ';', with keys and values separated by '='.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_011: [The constructor shall save the IoT Hub hostname as the value of 'hostName' in the connection string.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_012: [The constructor shall save the first part of the IoT Hub hostname as the value of `hubName`, hostname split by `.`.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_014: [The constructor shall save the device key as the value of 'sharedAccessKey' in the connection string.] */
    @Test
    public void IotHubConnectionStringSharedAccessKeySuccess() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";

        // act
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);

        // assert
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_020: [The constructor shall save the IoT Hub hostname as the value of `hostName` in the connection string.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_021: [The constructor shall save the first part of the IoT Hub hostname as the value of `hubName`, hostname split by `.`.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_022: [The constructor shall save the device ID as the UTF-8 URL-decoded value of `deviceId` in the connection string.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_023: [The constructor shall save the device key as the value of `sharedAccessKey` in the connection string.] */
    @Test
    public void IotHubConnectionStringParametersSharedAccessKeySuccess() throws ClassNotFoundException
    {
        // act
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);

        // assert
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_010: [The constructor shall interpret the connection string as a set of key-value pairs delimited by ';', with keys and values separated by '='.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_015: [The constructor shall save the shared access token as the value of 'sharedAccessToken' in the connection string.] */
    @Test
    public void IotHubConnectionStringSharedAccessTokenSuccess() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessSignature=" + VALID_SHARED_ACCESS_TOKEN + ";";

        // act
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);

        // assert
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, VALID_DEVICEID, null, VALID_SHARED_ACCESS_TOKEN);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_024: [The constructor shall save the shared access token as the value of `sharedAccessToken` in the connection string.] */
    @Test
    public void IotHubConnectionStringParametersSharedAccessTokenSuccess() throws ClassNotFoundException
    {
        // act
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, null, VALID_SHARED_ACCESS_TOKEN);

        // assert
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, VALID_DEVICEID, null, VALID_SHARED_ACCESS_TOKEN);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_013: [The constructor shall save the device ID as the UTF-8 URL-decoded value of 'deviceId' in the connection string.] */
    @Test
    public void IotHubConnectionStringDeviceIdSuccess() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=test%3Bdevice;SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";

        // act
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);

        // assert
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, "test;device", VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_022: [The constructor shall save the device ID as the UTF-8 URL-decoded value of `deviceId` in the connection string.] */
    @Test
    public void IotHubConnectionStringParametersDeviceIdSuccess() throws ClassNotFoundException
    {
        // act
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, "test;device", VALID_SHARED_ACCESS_KEY, null);

        // assert
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, "test;device", VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_016: [If the connection string is null or empty, the constructor shall throw an IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringNullConnectionStringThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString = null;

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_016: [If the connection string is null or empty, the constructor shall throw an IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringEmptyConnectionStringThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString = "";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_001: [A valid `hostName` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringMissingHostNameThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_001: [A valid `hostName` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringEmptyHostNameThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=;CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_001: [A valid `hostName` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersNullHostNameThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                null, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_001: [A valid `hostName` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersEmptyHostNameThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                "", VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_002: [A valid `hostName` shall be a valid URI.] */
    @Test (expected = URISyntaxException.class)
    public void IotHubConnectionStringHostNameNotURIThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=iot Hub Name.azure.net;CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";SharedAccessSignature=" + VALID_SHARED_ACCESS_TOKEN;

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_002: [A valid `hostName` shall be a valid URI.] */
    @Test (expected = URISyntaxException.class)
    public void IotHubConnectionStringParametersHostNameNotURIThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                "iot hub.azure.net", VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_003: [A valid `hostName` shall contain at least one `.`.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringNoHubNameThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=azure;CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";SharedAccessSignature=" + VALID_SHARED_ACCESS_TOKEN;

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_003: [A valid `hostName` shall contain at least one `.`.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersNoHubNameThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                "iothub", VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_004: [A valid `deviceId` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringMissingDeviceIdThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_004: [A valid `deviceId` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringEmptyDeviceIdThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=;SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_004: [A valid `deviceId` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersNullDeviceIdThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, null, VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_004: [A valid `deviceId` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersEmptyDeviceIdThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, "", VALID_SHARED_ACCESS_KEY, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_005: [A valid connectionString shall contain a `sharedAccessToken` or a `sharedAccessKey`.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringMissingAccessKeyAndTokenThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID;

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_006: [If provided, the `sharedAccessToken` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionString_emptyAccessToken_failed() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessSignature=";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_007: [If provided, the `sharedAccessKey` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringEmptyAccessKeyThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_005: [A valid connectionString shall contain a `sharedAccessToken` or a `sharedAccessKey`.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersNullAccessKeyThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, null, null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_006: [If provided, the `sharedAccessToken` shall not be null or empty.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_007: [If provided, the `sharedAccessKey` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersEmptyAccessKeyThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, "", null);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_006: [If provided, the `sharedAccessToken` shall not be null or empty.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_007: [If provided, the `sharedAccessKey` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersEmptyAccessTokenThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, null, "");
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_006: [If provided, the `sharedAccessToken` shall not be null or empty.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_007: [If provided, the `sharedAccessKey` shall not be null or empty.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersBothEmptyAccessKeyAndTokenThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, "", "");
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_008: [A valid connectionString shall not contain both `sharedAccessToken` and `sharedAccessKey` at the same time.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringBothValidKeyEmptyTokenThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";SharedAccessSignature=";

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_008: [A valid connectionString shall not contain both `sharedAccessToken` and `sharedAccessKey` at the same time.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringBothEmptyKeyValidTokenThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=;SharedAccessSignature=" + VALID_SHARED_ACCESS_TOKEN;

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_017: [If the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_008: [A valid connectionString shall not contain both `sharedAccessToken` and `sharedAccessKey` at the same time.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringBothValidKeyValidTokenThrows() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";SharedAccessSignature=" + VALID_SHARED_ACCESS_TOKEN;

        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_008: [A valid connectionString shall not contain both `sharedAccessToken` and `sharedAccessKey` at the same time.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersBothValidKeyEmptyTokenThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, "");
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_008: [A valid connectionString shall not contain both `sharedAccessToken` and `sharedAccessKey` at the same time.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersBothEmptyKeyValidTokenThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, "", VALID_SHARED_ACCESS_TOKEN);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_025: [If the parameters for the connection string is not valid, the constructor shall throw an IllegalArgumentException.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_008: [A valid connectionString shall not contain both `sharedAccessToken` and `sharedAccessKey` at the same time.] */
    @Test (expected = IllegalArgumentException.class)
    public void IotHubConnectionStringParametersBothValidKeyValidTokenThrows() throws ClassNotFoundException
    {
        // act
        Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[]{String.class, String.class, String.class, String.class},
                VALID_HOSTNAME, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, VALID_SHARED_ACCESS_TOKEN);
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_030: [The getHostName shall return the stored host name.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_031: [The getHubName shall return the stored hub name, which is the first part of the hostName.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_032: [The getDeviceId shall return the stored device id.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_033: [The getSharedAccessKey shall return the stored shared access key.] */
    @Test
    public void IotHubConnectionString_getters_sharedAccessKey_success() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessKey=" + VALID_SHARED_ACCESS_KEY + ";";
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, VALID_DEVICEID, VALID_SHARED_ACCESS_KEY, null);

        // act
        // assert
        assertEquals(VALID_HOSTNAME, Deencapsulation.invoke(iotHubConnectionString, "getHostName"));
        assertEquals(VALID_HUBNAME, Deencapsulation.invoke(iotHubConnectionString, "getHubName"));
        assertEquals(VALID_DEVICEID, Deencapsulation.invoke(iotHubConnectionString, "getDeviceId"));
        assertEquals(VALID_SHARED_ACCESS_KEY, Deencapsulation.invoke(iotHubConnectionString, "getSharedAccessKey"));
    }

    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_030: [The getHostName shall return the stored host name.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_031: [The getHubName shall return the stored hub name, which is the first part of the hostName.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_032: [The getDeviceId shall return the stored device id.] */
    /* Tests_SRS_IOTHUB_CONNECTIONSTRING_21_034: [The getSharedAccessToken shall return the stored shared access token.] */
    @Test
    public void IotHubConnectionString_getters_sharedAccessToken_success() throws ClassNotFoundException
    {
        // arrange
        final String connString =
                "HostName=" + VALID_HOSTNAME + ";CredentialType=SharedAccessKey;CredentialScope=Device;" +
                        "DeviceId=" + VALID_DEVICEID + ";SharedAccessSignature=" + VALID_SHARED_ACCESS_TOKEN + ";";
        Object iotHubConnectionString = Deencapsulation.newInstance(Class.forName(IOTHUB_CONNECTION_STRING_CLASS),
                new Class[] {String.class}, connString);
        assertConnectionString(iotHubConnectionString, VALID_HOSTNAME, VALID_DEVICEID, null, VALID_SHARED_ACCESS_TOKEN);

        // act
        // assert
        assertEquals(VALID_HOSTNAME, Deencapsulation.invoke(iotHubConnectionString, "getHostName"));
        assertEquals(VALID_HUBNAME, Deencapsulation.invoke(iotHubConnectionString, "getHubName"));
        assertEquals(VALID_DEVICEID, Deencapsulation.invoke(iotHubConnectionString, "getDeviceId"));
        assertEquals(VALID_SHARED_ACCESS_TOKEN, Deencapsulation.invoke(iotHubConnectionString, "getSharedAccessToken"));
    }
}