$ErrorActionPreference = 'Stop'

$workflowFiles = Get-ChildItem -Path $PSScriptRoot -Filter '*.yml'
foreach ($workflow in $workflowFiles) {
    $content = Get-Content -Raw $workflow.FullName
    $gradleMatches = [regex]::Matches($content, '(?m)^\s*run:\s*.*gradlew')
    if ($gradleMatches.Count -eq 0) { continue }

    $restoreIndex = $content.IndexOf('name: Restore Firebase configuration', [StringComparison]::Ordinal)
    if ($restoreIndex -lt 0) { throw "$($workflow.Name): missing Firebase restore step" }
    if ($content.IndexOf('FIREBASE_GOOGLE_SERVICES_JSON_BASE64', $restoreIndex, [StringComparison]::Ordinal) -lt 0) { throw "$($workflow.Name): restore step does not use the required secret" }
    if ($content.IndexOf('test -s app/google-services.json', $restoreIndex, [StringComparison]::Ordinal) -lt 0) { throw "$($workflow.Name): restore step does not verify a non-empty file" }
    $firstGradleIndex = $gradleMatches[0].Index
    if ($restoreIndex -gt $firstGradleIndex) { throw "$($workflow.Name): restore step must precede Gradle tasks" }
    if ($content.IndexOf('run: rm -f app/google-services.json', [StringComparison]::Ordinal) -lt 0) { throw "$($workflow.Name): missing Firebase cleanup step" }
    if ($content -match '(?m)^\s*run:\s*.*(cat|echo).*FIREBASE_GOOGLE_SERVICES_JSON_BASE64') { throw "$($workflow.Name): secret may be printed" }
}

Write-Host 'Workflow Firebase restore checks passed.'
