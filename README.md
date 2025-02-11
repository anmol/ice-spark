# Handle Iceberg Data with Apache Spark on AWS
This project aims to provide read/write APIs for Iceberg data stored in S3.
The data can be configured to use EMR runtime to handle data managed by AWS Lake Formation.


## EMR Setup
For Lake Formation to work with EMR we need to enable Runtime Role. The data can 
only be accessed when the Instance Profile and the Runtime roles are properly configured.
Further we need to specify correct spark configuration which configures a glue catalog for the Iceberg
Read/Write APIs

### EMR Instance Profile Role [ EMR_EC2_TestRole ]

#### Trust Relationships
Standard EC2 trust relationship

#### Policy
Apart from usual S3 permissions for the log bucket and other common EMR permissions
we need to add following to allow the Amazon EC2 instance profile role to assume the runtime roles.
```json
{
"Sid": "AllowRuntimeRoleUsage",
"Effect": "Allow",
"Action": [
"sts:AssumeRole",
"sts:TagSession"
],
"Resource": [
"arn:aws:iam::<account-id>:role/Test_EMR_Runtime_Role"
]
}
```


### EMR Runtime Role [ Test_EMR_Runtime_Role ]

#### Trust Relationships
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": "arn:aws:iam::<account-id>:role/EMR_EC2_TestRole"
            },
            "Action": "sts:AssumeRole"
        },
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": "arn:aws:iam::<account-id>:role/EMR_EC2_TestRole"
            },
            "Action": "sts:SetSourceIdentity"
        },
        {
            "Effect": "Allow",
            "Principal": {
                "AWS": "arn:aws:iam::<account-id>:role/EMR_EC2_TestRole"
            },
            "Action": "sts:TagSession",
            "Condition": {
                "StringEquals": {
                    "aws:RequestTag/LakeFormationAuthorizedCaller": "Amazon EMR"
                }
            }
        }
    ]
}
```

#### Policy
Apart from usual S3 permissions for the jar location and other Glue catalog permissions
we need to add following to allow the Amazon EC2 instance profile role to assume the runtime roles.
Data locations should not be added if you want them to be managed by Lake Formation


### Useful Docs
https://docs.aws.amazon.com/emr/latest/ManagementGuide/emr-lake-formation.html
https://docs.aws.amazon.com/emr/latest/ManagementGuide/emr-lf-enable.html
https://docs.aws.amazon.com/emr/latest/ManagementGuide/iceberg-with-lake-formation.html
https://docs.aws.amazon.com/emr/latest/ManagementGuide/emr-steps-runtime-roles.html

### AWS CLI commands
#### Create Cluster Example

#### Add Step Example