node {
    stage('InstallAzureMLCLI'){
        powershell(
        '''
$SQLServer = "LAPTOP-MBIFL4M4\\DATAMIGRATIONPOC"
$SQLDBName = "DataMigrationPOC"
$TGTSQLDBName = "DataMigrationPOC-tgt"
$uid ="sa"
$pwd = "Raja@1234"
$SqlQuery = "SELECT * from Audit_trail;"
$SourceSqlConnection = New-Object System.Data.SqlClient.SqlConnection
$TargetSqlConnection = New-Object System.Data.SqlClient.SqlConnection
$SourceSqlConnection.ConnectionString = "Server = $SQLServer; Database = $SQLDBName; Integrated Security = True; User ID = $uid; Password = $pwd;"
$TargetSqlConnection.ConnectionString = "Server = $SQLServer; Database = $TGTSQLDBName; Integrated Security = True; User ID = $uid; Password = $pwd;"
$SourceSqlConnection.Open()
$query = “select * from Audit_trail where audit_number<107 and table_name = 'fw_user_detail' and audit_action_id =2”

$Sourcecommand = $SourceSqlConnection.CreateCommand()
$Sourcecommand.CommandText = $query

$Targetcommand = $TargetSqlConnection.CreateCommand()


$result = $Sourcecommand.ExecuteReader()
$AuditLogs = new-object “System.Data.DataTable”
$AuditLogs.Load($result)
$SourceSqlConnection.Close()
 
 foreach($log in $AuditLogs)
 {
 if($log.audit_action_id -eq 1)
 {
    $selectquery = "SELECT * FROM $($log.table_name) where user_id = $($log.user_id)"
    $Sourcecommand.CommandText = $selectquery
    $insertQuery = "insert into $($log.table_name)" 
 }
 if($log.audit_action_id -eq 2)
 {
 $TargetSqlConnection.Open()
 $updateQuery = "UPDATE $($log.table_name) SET $($log.table_field_name) = '$($log.new_value)' WHERE user_id=$($log.user_id);"
 write-Host  $updateQuery
 $Targetcommand.CommandText = $updateQuery
 $result = $Targetcommand.ExecuteNonQuery()
 $TargetSqlConnection.Close()
 }
   Write-Host "$($log.audit_trail_id)";    
 }
        '''
        )
    }
}
