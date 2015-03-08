function gerarSelecaoCurriculo(row, obj){
    obj.selecionado = 'N';
    row.cells[0].innerHTML = "<input type='checkbox' name='chkSel_"+obj.idCurriculo+"' id='chkSel_"+obj.idCurriculo+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblCurriculos\")' />";
}
