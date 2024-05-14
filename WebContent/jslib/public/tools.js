Ext.namespace('tools');

Ext.apply(Ext.form.field.VTypes, {
  daterange: function (val, field) {
    var date = field.parseDate(val);

    if (!date) {
      return false;
    }
    if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
      var start = field.up('form').down('#' + field.startDateField);
      start.setMaxValue(date);
      start.validate();
      this.dateRangeMax = date;
    }
    else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
      var end = field.up('form').down('#' + field.endDateField);
      end.setMinValue(date);
      end.validate();
      this.dateRangeMin = date;
    }

    return true;
  },

  daterangeText: '开始日期不能大于结束日期！',

  password: function (val, field) {
    if (field.initialPassField) {
      var pwd = field.up('form').down('#' + field.initialPassField);
      return (val == pwd.getValue());
    }
    return true;
  },
  
  ispasswdText: '密码要求最少8位、要求有数字、字母、大小写！',
  
  isnumber: function (val, field) {
    return /^\d+$/.test(val);
  },
  isinteger: function (val,field){
    
    return /^[0-9]*[1-9][0-9]*$/.test(val);
  },
  isintegerText: '必须输入有效的正整数',
  isnumberText: '必须为有效的数字！',
  
  isinteger2: function (val,field){
	  
	  return /^([0-9]|[0-9]*[1-9][0-9])*$/.test(val);
  },
  isinteger2Text: '必须输入有效的整数(包含0)',
  

  isdecimal1: function (val, field) {
    return /^\d+$|^\d+\.\d{1}$/.test(val);
  },

  isdecimal1Text: '必须为有效的数字,小数点后不能超过2位！',

  isdecimal3: function (val, field) {
    return /^\d+$|^\d+\.\d{1,3}$/.test(val);
  },

  isdecimal3Text: '必须为有效的数字,小数点后不能超过3位！',
  
  isdecimal4: function (val, field) {
    return /^\d+$|^\d+\.\d{1,4}$/.test(val);
  },

  isdecimal4Text: '必须为有效的数字,小数点后不能超过4位！',
  
  isdecimal6: function (val, field) {
    return /^\d+$|^\d+\.\d{1,6}$/.test(val);
  },

  isdecimal6Text: '必须为有效的数字,小数点后不能超过6位！',
  
  isdecimal10: function (val, field) {
    return /^\d+$|^\d+\.\d{1,10}$/.test(val);
  },

  isdecimal10Text: '必须为有效的数字,小数点后不能超过10位！',
  
  ismoney: function (val, field) {    
    return /^\d+$|^\d+\.\d{1,2}$/.test(val);
  },

  ismoneyText: '必须为有效的数字,小数点后不能超过3位！',
  
  ismonth:function(val,field){
    return /^([1-9]|1[0-2])$/.test(val);
  },
  
  ismonthText:'月份只能为1到12个月',
 
  isyear:function(val,field){
    return /^[1-9]\d{3}$/.test(val);
  },
  
  isyearText:'请输入表示年的四位数字',
    
  isphone: function (val, field) {  
    return  /^1[3-8]\d{9}$|^(0\d{2,3}-)?\d{7,8}$/.test(val);
  },

  isphoneText: '请输入手机号或电话号码！',
  
  is4p2:function(val,field){
    return /^\d{1,2}(\.\d{1,2})?$/.test(val);
  },
  
  is4p2Text:'请输入整数2位，小数2位的数字',
  
  is5p2:function(val,field){
    return /^\d{1,3}(\.\d{1,2})?$/.test(val);
  },
  
  is5p2Text:'请输入整数3位，小数2位的数字',
  
  is6p2:function(val,field){
    return /^\d{1,4}(\.\d{1,2})?$/.test(val);
  },
  
  is6p2Text:'请输入整数4位，小数2位的数字',
  
  is15p2:function(val,field){
    return /^\d{1,13}(\.\d{1,2})?$/.test(val);
  },
  
  is15p2Text:'请输入整数13位，小数2位的数字',
  
  is8p2:function(val,field){
    return /^\d{1,6}(\.\d{1,2})?$/.test(val);
  },
  
  is8p2Text:'请输入整数6位，小数2位的数字',
  
  is9p2:function(val,field){
    return /^\d{1,7}(\.\d{1,2})?$/.test(val);
  },
  
  is9p2Text:'请输入整数7位，小数2位的数字',
  
  is10p2:function(val,field){
    return /^\d{1,8}(\.\d{1,2})?$/.test(val);
  },
  
  is10p2Text:'请输入整数8位，小数2位的数字',
  
  is12p2:function(val,field){
    return /^\d{1,10}(\.\d{1,2})?$/.test(val);
  },
  
  is12p2Text:'请输入整数13位，小数2位的数字'
});

Array.prototype.remove = function (dx) {
  if (isNaN(dx) || dx > this.length) { return false; }
  for (var i = 0, n = 0; i < this.length; i++) {
    if (i != dx) {
      this[n++] = this[i];
    }
  }
  this.pop();
};

Array.prototype.indexOf = function(value) {
  for(var i=0, l=this.length; i<l; i++)
    if(this[i] == value)
      return i;
   
  return -1;
};

tools.JsonReplace = function(text) {
	return text.replace(/\\/g,'').replace(/\"(new Date\((-*)\d+\))\"/g,'$1');
};

//换行
tools.ReplaceValue = function (str) {

  var result = "";
  if (str){
    var en = new RegExp('///n','g'); 
    var er = new RegExp('///r','g');
    var et = new RegExp('///t','g');
    var el = new RegExp('“','g');
    var eg = new RegExp('”','g');
    result = str.replace(en,'\n').replace(er,'\r').replace(et,'\t').replace(el,'"').replace(eg,'"')
    return result;
  }
  else
    return result;
};


tools.CheckPasswd = function (passwd) { 
	if(passwd.length < 8){   
	    return false;   
	} 
	
	var ls = 0; 
	if(passwd.match(/([a-z])+/)){   
	    ls++;   
	}   
	
	if(passwd.match(/([0-9])+/)){   
	    ls++;     
	}   
	
	if(passwd.match(/([A-Z])+/)){   
	    ls++;   
	}   
	
	if(passwd.match(/[^a-zA-Z0-9]+/)){ 
	    ls++;   
	}   

	return ls > 0; 
};   

tools.GetUrl = function (url) {
  url = url || '';

  return url;
};

tools.alert = function (message) {
  Ext.Msg.alert({title:'提示', msg:message,icon:Ext.Msg.INFO, buttons:Ext.Msg.OK,cls:'alertindex'});
};

tools.tranlogalert = function (message) {
  Ext.Msg.alert({title:'操作详细', msg:message,icon:Ext.Msg.INFO, buttons:Ext.Msg.OK,cls:'alertindex'});
};

tools.returnalert = function (message, fn, scope) {
  Ext.Msg.alert({title:'提示', msg:message,icon:Ext.Msg.INFO, buttons:Ext.Msg.OK,cls:'alertindex', fn: fn, scope: scope});
};

tools.ErrorAlert = function (action) {
  if (action.result && action.result.msg)
  	tools.alert(action.result.msg);
  else
    tools.alert("未知的系统错误！");
};

tools.ResponseAlert = function (response, success, failure) {
  try {
    var rtn = Ext.decode(response.responseText.replace(/\r\n/g,'<br/>').replace(/\\/g,''));

    if (!rtn.msg) rtn.msg = "未知的系统错误！";

    if (rtn && rtn.msg)
    	tools.alert(rtn.msg);

    if (rtn && rtn.success && success)
      success();

    if (rtn && !rtn.success && failure)
      failure();
  }
  catch (e) {
  	tools.alert('未知的系统错误！');
  }
};

tools.actionalert = function (action) {
	if (action && action.result && action.result.msg)
		tools.alert(action.result.msg);
	else
		tools.alert("未知的错误信息");	
};

tools.JsonGet = function (url, params) {
	var data = [];

	params = params || {};

	Ext.Ajax.request({
		url: url,
		params: params,
		async: false,
		success: function (response, opts) {
			//alert(response.responseText);
			try {
				data = Ext.decode(response.responseText.replace(/\r\n/g,'<br/>').replace(/\"(new Date\((-*)\d+\))\"/g,'$1'));
			}
			catch(ex) {
				alert("JsonGet:" + ex.toString());
			}
			
		},
		failure: function (response) { }
	});

	return data;
};

tools.HtmlGet = function (url, params) {
  var rtn = '';
  
  params = params || {};

  Ext.Ajax.request({
    url: url,
    params: params,
    async: false,
    success: function (response, opts) {
      rtn = response.responseText;      
    },
    failure: function (response) { }
  });

  return rtn;
};

tools.FormCheckCombo = function (label, name, id, store, anchor, blank, tab, labelwidth) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var cb = Ext.create('Ext.ux.CheckCombo', {
    fieldLabel: label, name: name, id: id,
    mode: 'local', triggerAction: 'all', forceSelection: true, selectOnFocus: false, 
    editable: true, addAllSelector: true, queryMode: 'local', labelWidth: lw,
    valueField: 'id', displayField: 'name', allowBlank: blank, blankText: '请选择' + label + '！', store: store,
    anchor: anchor, tabIndex: tab
  });
  
  return cb;
};

tools.FormCombo = function (label, name, id, store, anchor, blank, tab, labelwidth, isinit) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var cb = Ext.create('Ext.form.ComboBox', {
    fieldLabel: label, name: name, id: id,
    mode: 'local', triggerAction: 'all', forceSelection: true, selectOnFocus: false, 
    editable: true, queryMode: 'local',labelWidth: lw,
    valueField: 'id', displayField: 'name', allowBlank: blank, blankText: '请选择' + label + '！', store: store,
    anchor: anchor, tabIndex: tab
  });

  if (isinit) return cb;

  if (cb.store.data.length > 0)
    cb.setValue(cb.store.getAt(0).data.id);
  
  return cb;
};

tools.FormText = function (label, name, id, max, anchor, blank, tab, vtype, labelwidth) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var ab = Ext.create('Ext.form.TextField', {
    fieldLabel: label, name: name, id: id,
    maxLength: max, maxLengthText: label + '长度不能超过' + max + '个字符！', selectOnFocus: false, labelWidth: lw,
    blankText: label + '不能为空！', allowBlank: blank, anchor: anchor, vtype: vtype, tabIndex: tab
  });
  
  return ab;
};

tools.FormTextFlex = function (label, name, id, max, anchor, blank, tab, vtype, labelwidth) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var ab = Ext.create('Ext.form.TextField', {
    fieldLabel: label, name: name, id: id,
    maxLength: max, maxLengthText: label + '长度不能超过' + max + '个字符！', selectOnFocus: false, labelWidth: lw,
    blankText: label + '不能为空！', allowBlank: blank, flex: 1, vtype: vtype, tabIndex: tab
  });
  
  return ab;
};

tools.FormTextArea = function (label, name, id, max, anchor, blank, tab, rows, labelwidth) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var ab = Ext.create('Ext.form.TextArea', {
    fieldLabel: label, name: name, id: id,
    maxLength: max, maxLengthText: label + '长度不能超过' + max + '个字符！', selectOnFocus: false, labelWidth: lw,
    blankText: label + '不能为空！', allowBlank: blank, anchor: anchor, tabIndex: tab, rows: rows
  });
  
  return ab;
};

tools.FormDate = function (label, name, id, format, anchor, blank, tab, value, labelwidth,hidden) {
  var nowdate = null;

  if (!blank && !value)
    nowdate = new Date();
  else if (value)
    nowdate = value;

  var lw = 80;

  if (labelwidth) lw = labelwidth;

  return Ext.create('Ext.form.DateField', {
    fieldLabel: label, name: name, id: id, format: format, value: nowdate, submitFormat: 'Y-m-d',labelWidth: lw,
    selectOnFocus: false, blankText: label + '不能为空！', allowBlank: blank, anchor: anchor, tabIndex: tab,hidden:hidden
  });
};

tools.FormNoEditDate = function (label, name, id, format, anchor, blank, tab, value, labelwidth) {
  var nowdate = null;

  if (!blank && !value)
    nowdate = new Date();
  else if (value)
    nowdate = value;

  var lw = 80;

  if (labelwidth) lw = labelwidth;

  return Ext.create('Ext.form.DateField', {
    fieldLabel: label, name: name, id: id, editable: false, format: format, value: nowdate, submitFormat: 'Y-m-d H:i:s',labelWidth: lw,
    selectOnFocus: false, blankText: label + '不能为空！', allowBlank: blank, anchor: anchor, tabIndex: tab
  });
};

tools.FormCheck = function (label, name, id, checked, tab) {
  var cb = Ext.create('Ext.form.Checkbox', {
    boxLabel: label, name: name, id: id, checked: checked, inputValue: 1, tabIndex: tab, height: 22 
  });
  
  cb.inputValue = 'true';
  cb.uncheckedValue = 'false';
  return cb;
};

tools.GetParam = function (param) {
	if (gpersist.AllParams) {
		
		if (Ext.isArray(gpersist.AllParams.get(param))) {
			var store = new Ext.data.JsonStore({
	      fields: ['id', 'name'],
	      data: gpersist.AllParams.get(param)
	    });
			
			return store;
		}
	}
	
	return null;
};

tools.DeleteParam = function (param) {
  if (Ext.isDefined(gpersist.AllParams)) {
    if (gpersist.AllParams.get(param) == null) 
      return;
    
    gpersist.AllParams.removeAtKey(param);
  }  
};

tools.GetParams = function (params) {
  if (!Ext.isDefined(gpersist.AllParams))
    gpersist.AllParams = Ext.create('Ext.util.MixedCollection');

  var getparams = [];
  Ext.each(params, function (param, index) { 
  	if (gpersist.AllParams.get(param) == null) {
  		getparams.push(param);
  	}
  });

  if (getparams.length > 0) {
  	Ext.Ajax.request({
	    url: gpersist.ACTION_SYS_LISTSELECTS,
	    params: { selects: Ext.encode(getparams) },
	    async: false,
	    method: 'POST',
	    success: function (response, opts) {
	      var params = Ext.decode(response.responseText);

	      Ext.each(params, function (param, index) {
	        //var store = new Ext.data.JsonStore({
	        //  fields: ['id', 'name'],
	        //  data: param.pmtdata
	        //});

	        gpersist.AllParams.add(param.pmtname, param.pmtdata);
	      });
	    },
	    failure: function (response) {
	    }
	  });
  }
};

tools.GetParamByUrl = function (name, url, params) {
  if (!Ext.isDefined(gpersist.AllParams))
    gpersist.AllParams = Ext.create('Ext.util.MixedCollection');
  
  if (gpersist.AllParams && gpersist.AllParams.get(name))
  	tools.DeleteParam(name);
  
  params = params || {};
  
  Ext.Ajax.request({
    url: url,
    params: params,
    async: false,
    method: 'POST',
    success: function (response, opts) {
      var rtn = Ext.decode(response.responseText);      

      gpersist.AllParams.add(name, rtn);
    },
    failure: function (response) {
    }
  });
};

tools.GetNullStore = function (type) {  
  var typedata = [];
  
  if (type) {
    if (type == gpersist.SELECT_ALL_VALUE) {
      typedata.push({ id: gpersist.SELECT_ALL_VALUE, name: '--选择全部--' });
    }
    else if (type == gpersist.SELECT_NULL_VALUE) {
      typedata.push({ id: gpersist.SELECT_NULL_VALUE, name: '--无选择--' });
    }
    else if (type == gpersist.SELECT_MUST_VALUE) {
      typedata.push({ id: gpersist.SELECT_MUST_VALUE, name: '--请选择--' });
    }
    
    return new Ext.data.JsonStore({
      fields: ['id', 'name'],
      data: typedata
    });
  }
  else  
  	return new Ext.data.JsonStore({
  		fields: ['id', 'name'],
  		data: []
  	});
};

tools.ComboStore = function (param, type) {
  var data = [];
  var typedata = [];

  if (type) {
    if (type == gpersist.SELECT_ALL_VALUE) {
      typedata.push({ id: gpersist.SELECT_ALL_VALUE, name: '--选择全部--' });
    }
    else if (type == gpersist.SELECT_NULL_VALUE) {
      typedata.push({ id: gpersist.SELECT_NULL_VALUE, name: '--无选择--' });
    }
    else if (type == gpersist.SELECT_MUST_VALUE) {
      typedata.push({ id: gpersist.SELECT_MUST_VALUE, name: '--请选择--' });
    }
  }

  if (gpersist.AllParams && gpersist.AllParams.get(param))
  	data = gpersist.AllParams.get(param);
  else {
  	tools.GetParams([param]);
  	
  	if (gpersist.AllParams)
  		data = gpersist.AllParams.get(param);
  } 

  if (!Ext.isDefined(data))
    data = [];

  return new Ext.data.JsonStore({
    fields: ['id', 'name'],
    data: typedata.concat(data)
  });
};

tools.ComboStoreByUrl = function (url, params, type) {
  params = params || {};
  
  var typedata = [];
  var rtn = [];
  
  if (type) {
    if (type == gpersist.SELECT_ALL_VALUE) {
      typedata.push({ id: gpersist.SELECT_ALL_VALUE, name: '--选择全部--' });
    }
    else if (type == gpersist.SELECT_NULL_VALUE) {
      typedata.push({ id: gpersist.SELECT_NULL_VALUE, name: '--无选择--' });
    }
    else if (type == gpersist.SELECT_MUST_VALUE) {
      typedata.push({ id: gpersist.SELECT_MUST_VALUE, name: '--请选择--' });
    }
  }
  
  Ext.Ajax.request({
    url: url,
    params: params,
    async: false,
    method: 'POST',
    success: function (response, opts) {
      rtn = Ext.decode(response.responseText);   
    },
    failure: function (response) {
    }
  });
  
  return new Ext.data.JsonStore({
    fields: ['id', 'name'],
    data: typedata.concat(rtn)
  });
};

tools.GridComboRenderer = function (store) {
  return function (value) {

    var display = '';

    if (store) {
      for (var i = 0; i < store.getCount(); i++) {
        if (store.getAt(i).data.id == value) {
          display = store.getAt(i).data.name;
          break;
        }
      }
    }

    return display;
  };
};

tools.CnMoney = function (value, isprefix) {
  if (isprefix)
    return Ext.util.Format.currency(value, '￥', 2);
  else
    return Ext.util.Format.number(value, '0.00');
};

tools.GetExportColumn = function (grid) {
	var cols = [];
	for (var i = 0; i < grid.columns.length; i++) {
		var column = grid.columns[i]; 
		if ((column.dataIndex != 'IndexNum') && !column.isHidden()) { 
			var col = {};
			col.text = column.text;
			col.dataindex = column.dataIndex;
			col.width = column.width;
      col.format = column.colformat;
			cols.push(col);
		}
	}
	
	return Ext.encode(cols);
};

tools.GetGridColumns = function (sqlid, cols, fields, prefix, funcs, ischeck) {
  tools.GetGridColumnsByUrl('columns/' + sqlid + '.js', cols, fields, prefix, funcs, ischeck);
},

tools.GetGridColumnsByUrl = function (sqlid, cols, fields, prefix, funcs, ischeck) {
  var numberer = new Ext.grid.RowNumberer({
  	id:prefix+'_number',
    header: '序号',
    dataIndex: 'IndexNum',
    locked: true,
    width: 40,
    sortable: false
  });

  cols.push(numberer);

  if (ischeck) {
    var checker = Ext.create('Ext.ux.CheckColumn', {
    	id:prefix+'checkcolumn',
      header: '选择',
      dataIndex: 'CheckNum',
      width: 40
    });

    cols.push(checker);
  }

  if (!prefix)
    prefix = 'col_';

  if (Ext.isEmpty(sqlid)) return;
  
  Ext.Ajax.request({
    url: sqlid,
    scope: this,
    async: false,
    success: function (response, opts) {//alert(response.responseText);
      var items = Ext.decode(response.responseText);
      var col;
      var field;
      var state;
      var xtype;

      Ext.each(items, function (item, index) {
        if (item.isdisplay) {
          switch (item.colext) {
            case 'bool':
              xtype = 'Ext.ux.CheckColumn';
              break;

            default:
              xtype = 'Ext.grid.Column';
          }

          var editor = '';
          var store;
          if (item.isedit) {
            var etype = 'textfield';

            if (item.pmtsql && item.pmtsql != '') {
              store = tools.ComboStore(item.pmtsql);
              editor = { xtype: 'combobox', triggerAction: 'all', store: store, id: 
              	prefix + 'cb' + item.colcode, editable: false, selectOnTab: true,
                name: prefix + 'cb' + item.colcode, valueField: 'id', displayField: 'name', 
                queryMode: 'local', mode: 'local',
                allowBlank: false, blankText: '请选择' + item.colname + '！', listeners: { }
              };
            }
            else {
              switch (item.colext) {
                case 'int':
                  editor = { allowBlank: !item.isnull, vtype: 'isnumber', selectOnFocus: true };
                  break;

                case 'string':
                  editor = { allowBlank: !item.isnull, maxLength: item.collen, maxLengthText: '长度不能超过' + item.collen + '个字符！', selectOnFocus: true };
                  break;

                case 'float':
                  editor = { allowBlank: !item.isnull, vtype: item.vtype, selectOnFocus: true };
                  break;

                case 'date':
                  editor = { xtype:'datefield', allowBlank: false, selectOnFocus: true, format:item.colformat, submitFormat: item.colformat };
                  break;
                  
                case 'bool':
                  break;
              }
            }
          }

          col = Ext.create(xtype, {
            header: item.colname,
            dataIndex: item.colcode,
            id: prefix + item.colcode,            
            sortable: item.isorder,
            locked: item.islock,
            align: 'left',
            editor: editor
          });

          if (!item.isedit) {
            switch (item.colext) {
              case 'bool':
                col.on('beforecheck', function () {
                  this.isCancel = true;
                });
                break;
            }
          }
          
          if (item.colwidth == 0) {
            col.flex = item.flexcnt;
          }
          else
            col.width = item.colwidth;

          col.colformat = item.colformat;
          
          if (item.colformat != '') {
            switch (item.colext) {
              case 'float':
                col.renderer = Ext.util.Format.numberRenderer(item.colformat);
                break;
              case 'date':
                col.renderer = Ext.util.Format.dateRenderer(item.colformat);
                break;
            }
          }

          if (item.isedit && item.pmtsql && item.pmtsql != '') {
            col.renderer = tools.GridComboRenderer(store);
          }

          if (item.colrender && item.colrender != '') {
            if (funcs && funcs.getByKey(item.colrender)) {
              col.renderer = funcs.getByKey(item.colrender);
            }
          }

          if (item.summarytype && item.summarytype != '') {
            switch (item.summarytype) {
              case 'average':
                col.summaryType = 'average';

                if (item.summaryrender == '')
                  col.summaryrender = function (value, summaryData, dataIndex) {
                    return '平均: ' + tools.CnMoney(value);
                  };
                break;

              case 'count':
                col.summaryType = 'count';

                if (item.summaryrender == '')
                  col.summaryRenderer = function (value, summaryData, dataIndex) {
                    return '总数：' + value;
                  };
                break;

              case 'sum':
                col.summaryType = 'sum';

                if (item.summaryrender == '')
                  col.summaryRenderer = function (value, summaryData, dataIndex) {
                    return '总计: ' + value;
                  };
                break;

              default:
                if (funcs && funcs.getByKey(item.summarytype))
                  col.summaryType = funcs.getByKey(item.summarytype);

                if (item.summaryrender != '' && funcs && funcs.getByKey(item.summaryrender))
                  col.summaryRenderer = funcs.getByKey(item.summaryrender);
                break;
            }
          }

          cols.push(col);
        }

        field = new Ext.data.Field({
          name: item.colcode,
          mapping: item.colcode,
          type: item.colext
        });

        fields.push(field);
      });
    },
    failure: function () { }
  });
};

tools.SimpleColumnsByUrl = function (sqlid, cols, prefix) {
  if (!prefix)
    prefix = 'col_';

  if (Ext.isEmpty(sqlid)) return;
  
  Ext.Ajax.request({
    url: sqlid,
    scope: this,
    async: false,
    success: function (response, opts) {
      var items = Ext.decode(response.responseText);
      var col;
      var field;
      var state;
      var xtype = 'Ext.grid.Column';

      Ext.each(items, function (item, index) {
        if (item.isdisplay) {
          col = Ext.create(xtype, {
            header: item.colname,
            dataIndex: item.colcode,
            id: prefix + item.colcode,            
            sortable: item.isorder,
            locked: item.islock,
            width: item.colwidth,
            align: 'left'
          });
          
          cols.push(col);
        }
      });
    },
    failure: function () { }
  });
};

tools.CreateGridStore = function (url, fields, pagesize) {
  var size = pagesize || 20;

  return new Ext.data.JsonStore({
    proxy: {
      type: 'ajax',
      url: url,
      timeout: 180000,
      reader: {
        type: 'json',
        root: 'data',
        totalProperty: 'total'
      }
    },
    autoLoad: false,
    sortOnLoad: true,
    fields: fields,
    pageSize: size
  });
};

tools.SetValue = function (id, value) {
  var tb = Ext.getCmp(id);

  if (tb)
    tb.setValue(value);
};

tools.GetValue = function (id) {
  var tb = Ext.getCmp(id);

  if (tb)
    return tb.getValue();
  else
    return null;
};

tools.GetDisplayValue = function (id) {
  var tb = Ext.getCmp(id);

  if (tb)
    return tb.getDisplayValue();
  else
    return null;
};

tools.GetValueEncode = function (id) {
  var tb = Ext.getCmp(id);

  if (tb)
    return encodeURI(tb.getValue());
  else
    return null;
};

tools.GetEncode = function (value) {
  return encodeURI(value);
};

tools.ResetForm = function (form, unresets, mep) {
  form.getFields().each(function (f) { var date = new Date();
    var isreset = true;
    if (unresets) {
      Ext.each(unresets, function (id, idx) {
        if (f.id == mep + id) {
          isreset = false;
          return false;
        }
      });
    }

    if (isreset) f.reset(); 
    
    //if (typeof console == 'object') console.log(isreset + '-' + f.id + '-' + (new Date() - date));
  });
};

tools.ResetCombo = function (combos, prefix) {
	prefix = prefix || '';
	
	Ext.each(combos, function (combo, idx) {
		var cb = Ext.getCmp(prefix + combo);

		if (cb && (cb.store.data.length > 0))
			cb.setValue(cb.store.getAt(0).data.id);
	});
};

tools.Resets = function (ids, prefix) {
  prefix = prefix || '';
  
  Ext.each(ids, function (id, idx) {
  	var obj = Ext.getCmp(prefix + id);
  	
  	if (obj) obj.reset();
  });
};

tools.ResetNullCombo = function (combos, prefix) {
	prefix = prefix || '';
	
	Ext.each(combos, function (combo, idx) {
		var cb = Ext.getCmp(prefix + combo);

		cb.reset();
	});
};

tools.InvalidForm = function (form) {
  invalid = form.getFields().filterBy(function (field) {
    return !field.validate();
  });

  return (invalid.length < 1);
};

tools.BtnsDisable = function (ids, prefix) {
  prefix = prefix || '';
  
  Ext.each(ids, function (id, idx) {
    tools.BtnDisable(prefix + id);
  });
};

tools.BtnDisable = function (id) {
  var btn = Ext.getCmp(id);

  if (btn) btn.disable();
};

tools.BtnsEnable = function (ids, prefix) {
  prefix = prefix || '';
  
  Ext.each(ids, function (id, idx) {
    tools.BtnEnable(prefix + id);
  });
};

tools.BtnEnable = function (id) {
  var btn = Ext.getCmp(id);

  if (btn) btn.enable();
};

tools.Disabled = function (ids, prefix) {
  prefix = prefix || '';
  
  Ext.each(ids, function (id, idx) {
    var item = Ext.getCmp(prefix + id);
    if (item && item.inputEl && !item.readOnly) {
      if (item.xtype) {
        switch (item.xtype) {
          case 'combobox':
          case 'datefield':
          case 'checkcombo':
            item.readOnly = true; 
            break;
            
          default:
            item.setReadOnly(true);
            break;
        }
      }
      
      if (item.xtype && item.xtype == 'checkboxfield')
        return;      
      
      item.inputEl.addCls('editdisabled');
    }
  });
};

tools.Enabled = function (ids, prefix) {
  prefix = prefix || '';
  
  Ext.each(ids, function (id, idx) {
    var item = Ext.getCmp(prefix + id);

    if (item && item.inputEl && item.readOnly) {

      if (item.xtype) {
        switch (item.xtype) {
          case 'combobox':
          case 'datefield':
          case 'checkcombo':
            item.readOnly = false; 
            break;
            
          default:
            item.setReadOnly(false);
            break;
        }
      }
      
      if (item.xtype && item.xtype == 'checkboxfield')
        return;
      
      item.inputEl.removeCls('editdisabled');
    }
  });
};

tools.FormInit = function (diss, ens, prefix) {
  tools.Enabled(ens, prefix);
  tools.Disabled(diss, prefix);
};

tools.FormDisable = function (diss, ens, prefix) {
  tools.Disabled(ens, prefix);
  tools.Disabled(diss, prefix);
};

tools.OnGridLoad = function (grid, msg) {
  var selModel = grid.getSelectionModel();
  if (selModel.hasSelection()) {
    var selected = selModel.getSelection();

    return selected[0].data;
  }
  else {
//    if (msg && !Ext.isEmpty(msg))
//      tools.alert(msg);
//    else
      //tools.alert('请选择需要修改的数据！');
    return null;
  }
};

tools.GetToolBarCombo = function (name, id, width, label, lablewidth, store, value) {  
  var combo = Ext.create('Ext.form.field.ComboBox', {
    id: id,
    name: name,
    fieldLabel: label,
    labelWidth: lablewidth,
    width: width,
    store: store,
    displayField: 'name',
    valueField: 'id',
    typeAhead: true,
    queryMode: 'local',
    triggerAction: 'all',
    selectOnFocus: false,
    editable: true
  });

  if (combo.store.data.length > 0) {
    if (value)
      combo.setValue(value);
    else
      combo.setValue(combo.store.getAt(0).data.id);
  }

  return combo;
};

tools.GetToolBarCheckCombo = function (name, id, width, label, lablewidth, store, value) {  
  var combo = Ext.create('Ext.ux.CheckCombo', {
    id: id,
    name: name,
    fieldLabel: label,
    labelWidth: lablewidth,
    width: width,
    store: store,
    displayField: 'name',
    valueField: 'id',
    typeAhead: true,
    queryMode: 'local',
    triggerAction: 'all',
    selectOnFocus: true,
    editable: false,
    addAllSelector: true
  });

  if (value)
    combo.setValue(value);
  return combo;
};


tools.GetMenuAuth = function(mcode) {
	var sauth = '';
	
	if (gpersist && gpersist.UserInfo && gpersist.UserInfo.auths) {
		Ext.each(gpersist.UserInfo.auths, function (auth, index) {
	  	if (auth.mcode == mcode) {
	  		sauth = auth.mauth.toString(2);
	  		return false;
	  	}
	  });		
	}
	
	return sauth;
};

tools.GetAuth = function(mcode) {
  var rtn = 0;
  
  if (gpersist && gpersist.UserInfo && gpersist.UserInfo.auths) {
    Ext.each(gpersist.UserInfo.auths, function (auth, index) {
      if (auth.mcode == mcode) {
        rtn = auth.mauth;
        return;
      }
    });   
  }
  
  return rtn;
};

tools.ValidMenuAuth = function(auth, idx) {  
  if (idx < 1)
    return false;
  
  if ((auth & Math.pow(2, idx - 1)) == Math.pow(2, idx - 1))
    return true;
  
  return false;
};

tools.ValidSeparator = function(auth, sepauth) {    
  if ((auth | sepauth) > 0)
    return true;
  
  return false;
};

tools.DeleteToolbarItem = function (mcode, items, id, idx) {
	if (gpersist && gpersist.UserInfo && gpersist.UserInfo.auths) {
		var sauth = tools.GetAuth(mcode);

		tools.DeleteToolbarItemByAuth (sauth, items, mcode + id, idx);
	}
};

tools.DeleteToolbarItemByAuth = function (auth, items, id, idx, separator) {
	  if (!tools.ValidMenuAuth(auth, idx)) {
	    for (var i = 0; i < items.length; i++) {
	      if (items[i].id && (items[i].id == id)) {
	        if (i < items.length) {
	          items.remove(i);
	          if (i > 0)
	            items.remove(i - 1);
	          
	          if (separator && (i > 1))
	            items.remove(i - 2);
	        }
	        break;
	      }
	    }
	  }
};

tools.SetToolbar = function (items, mcode) {
	//var sauth = tools.GetMenuAuth(mcode);

	var sauth = tools.GetAuth(mcode);

	// 增加 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnAdd', 2, tools.ValidSeparator(sauth, 14));
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnCopy', 2);

  // 修改 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnEdit', 3);

  // 删除 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnDelete', 4);

  // 打印 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnPrint', 5);

  // 导出 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnExport', 6, tools.ValidSeparator(sauth, 48));

  // 处理 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnDeal', 7, tools.ValidSeparator(sauth, 64));

  // 有效无效 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnValid', 8, tools.ValidSeparator(sauth, 384));
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnInValid', 9);
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnCheck', 10, tools.ValidSeparator(sauth, 1536));
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnUnCheck', 11);
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnUpload', 12, tools.ValidSeparator(sauth, 2048));
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSpecial01', 13, tools.ValidSeparator(sauth, 28672));
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSpecial02', 14);
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSpecial03', 15);
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSubmit', 16);
};

tools.SetEditToolbar = function (items, mcode) {
  var sauth = tools.GetAuth(mcode);
  
  if (!tools.ValidMenuAuth(sauth, 2) && !tools.ValidMenuAuth(sauth, 3) && !tools.ValidMenuAuth(sauth, 7)) {
    tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSave', 2);
    tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSubmit', 2);
    tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormCancel', 2);
  }
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormCopy', 2);

  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormEdit', 3, tools.ValidSeparator(sauth, 6)); 
  
  // 打印 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnPrint', 5);

  // 导出 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnExport', 6, tools.ValidSeparator(sauth, 48));

  // 处理 
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormDeal', 7, tools.ValidSeparator(sauth, 64));

  if (!tools.ValidMenuAuth(sauth, 8) || !tools.ValidMenuAuth(sauth, 9)) {
    if (!tools.ValidMenuAuth(sauth, 8))
      tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormValid', 8, tools.ValidSeparator(sauth, 384));
    else if (!tools.ValidMenuAuth(sauth, 9))
      tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormValid', 9);
  }
  
  if (!tools.ValidMenuAuth(sauth, 10) && !tools.ValidMenuAuth(sauth, 11)) {
    if (!tools.ValidMenuAuth(sauth, 10))
      tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormCheck', 10, tools.ValidSeparator(sauth, 1536));
    else if (!tools.ValidMenuAuth(sauth, 11))
      tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormCheck', 11);
  }

  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormSpecial01', 13, tools.ValidSeparator(sauth, 28672));
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormSpecial02', 14);
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnFormSpecial03', 15);
  
  tools.DeleteToolbarItemByAuth(sauth, items, mcode + 'btnSubmit', 16);
};

tools.SetToolbarSave = function (items, mcode, id) {
	var sauth = tools.GetMenuAuth(mcode);

	var idxadd = 0;
	var idxedit = 0;
	var idxdelete = 0;
	
	if ((sauth.length < 2) || (sauth.substring(sauth.length - 2, sauth.length - 1) == '0')) idxadd = 1;
  if ((sauth.length < 3) || (sauth.substring(sauth.length - 3, sauth.length - 2) == '0')) idxedit = 1;
  if ((sauth.length < 4) || (sauth.substring(sauth.length - 4, sauth.length - 3) == '0')) idxdelete = 1;
  
  if ((idxadd == 1) && (idxedit == 1) && (idxdelete == 1))
  	tools.DeleteToolbarItemByAuth(sauth, items, mcode + id, 2);
};

tools.GetYearStore = function (type) {
  var nowdate = new Date();
  var years = [];

  if (type == gpersist.SELECT_ALL_VALUE) {
    years.push([gpersist.SELECT_ALL_VALUE, '--所有年份--']);
  }
  else if (type == gpersist.SELECT_MUST_VALUE) {
    years.push([gpersist.SELECT_MUST_VALUE, '--选择年份--']);
  }

  for (var i = nowdate.getFullYear() + 1; i >= 2015; i--) {
    years.push(['' + i, '' + i]);
  }

  return new Ext.data.ArrayStore({
    fields: ['id', 'name'],
    data: years
  });
};

tools.GetIntMonthStore = function (type) {
  var months = [];

  if (type == gpersist.SELECT_ALL_VALUE) {
    months.push([gpersist.SELECT_ALL_VALUE, '--所有月份--']);
  }
  else if (type == gpersist.SELECT_MUST_VALUE) {
    months.push([gpersist.SELECT_MUST_VALUE, '--选择月份--']);
  }
  
  months.push([1, '01']);
  months.push([2, '02']);
  months.push([3, '03']);
  months.push([4, '04']);
  months.push([5, '05']);
  months.push([6, '06']);
  months.push([7, '07']);
  months.push([8, '08']);
  months.push([9, '09']);
  months.push([10, '10']);
  months.push([11, '11']);
  months.push([12, '12']);

  return new Ext.data.ArrayStore({
    fields: ['id', 'name'],
    data: months
  });
};

tools.GetMonthStore = function (type) {
  var months = [];

  if (type == gpersist.SELECT_ALL_VALUE) {
      months.push([gpersist.SELECT_ALL_VALUE, '--所有月份--']);
  }

  months.push(['01', '01']);
  months.push(['02', '02']);
  months.push(['03', '03']);
  months.push(['04', '04']);
  months.push(['05', '05']);
  months.push(['06', '06']);
  months.push(['07', '07']);
  months.push(['08', '08']);
  months.push(['09', '09']);
  months.push(['10', '10']);
  months.push(['11', '11']);
  months.push(['12', '12']);

  return new Ext.data.ArrayStore({
      fields: ['id', 'name'],
      data: months
  });
};

tools.ObjectView = function (object) {
  var s = '';
  for(i in object){
    s += i + '<br />'; 
  }
  //document.write(s);
  tools.alert(s);
};

tools.GetMenus = function (root, fn, scope) {  
  var menus = Ext.create('Ext.menu.Menu', {
    style: { overflow: 'visible' }
  }); 

  if (root) {
    Ext.each(root, function (menu, index) { 
      menus.add({
        text: menu.text,
        handler: function () { if (fn) fn(menu); },
        scope: scope
      });
    });
  }
  
  return menus;
};

tools.GetUserName = function () {
  var me = this;
  var mep = me.tranPrefix;

  tools.GetUserNameByID(mep + 'userid', mep + 'username');  
};

tools.GetUserNameByID = function(ctrlid, ctrlname) {
  var userid = tools.GetValue(ctrlid);

  if (!Ext.isEmpty(userid)) {
    var item = tools.JsonGet(tools.GetUrl(gpersist.ACTION_USER_GETNAME + '?userid=') + userid);

    if (item && item.name && !Ext.isEmpty(item.name) && (item.name != 'null'))
      tools.SetValue(ctrlname, item.name);
    else
      tools.SetValue(ctrlname, '');
  }      
};

tools.SetCheckBoxValue = function (checkboxs, prefix) {
  Ext.each(checkboxs, function (id, idx) {
      var checkbox = Ext.getCmp(prefix + id);

      checkbox.inputValue = 'true';
      checkbox.uncheckedValue = 'false';
  });
};

tools.mergeCells = function(grid, cols) {  
  var arrayTable = document.getElementById(grid.getId()+"-body").getElementsByTagName('table'); 

  if (arrayTable.length < 2)
    return;

  var arrayTr = arrayTable[1].getElementsByTagName("tr");  

  var trCount = arrayTr.length;  
  var arrayTd;  
  var td;  
  
  var merge = function(rowspanObj, removeObjs){ 
    if(rowspanObj.rowspan != 1){  
      arrayTd =arrayTr[rowspanObj.tr].getElementsByTagName("td");  
      td=arrayTd[rowspanObj.td-1];  
      td.rowSpan=rowspanObj.rowspan;  
      td.vAlign="middle";               
      Ext.each(removeObjs,function(obj){  
        arrayTd =arrayTr[obj.tr].getElementsByTagName("td");  
        arrayTd[obj.td-1].style.display='none';                           
      });  
    }     
  };    

  var rowspanObj = {};     
  var removeObjs = []; 
  var col;  
  Ext.each(cols, function(colIndex) {
    var rowspan = 1;  
    var divHtml = null; 
    
    for (var i = 1; i < trCount; i++) {  
      arrayTd = arrayTr[i].getElementsByTagName("td");  
      var cold = 0;

      col= colIndex + cold;

      if (!divHtml) {  
        divHtml = arrayTd[col - 1].innerText; 
        rowspanObj = {tr: i, td: col, rowspan: rowspan};  
      } 
      else {  
        var cellText = arrayTd[col - 1].innerText;  
        
        var addf = function() {   
          rowspanObj["rowspan"] = rowspanObj["rowspan"] + 1;  
          removeObjs.push({tr: i, td: col});
            
          if(i == trCount-1)  
            merge(rowspanObj, removeObjs);   
        };  
        
        var mergef = function() {  
          merge(rowspanObj, removeObjs); 
          
          divHtml = cellText;  
          rowspanObj = {tr: i, td: col, rowspan: rowspan}; 
          removeObjs = [];  
        };  
        
        if (cellText == divHtml) {  
          if (colIndex != cols[0]) {
            var leftDisplay = arrayTd[col - 2].style.display;
            
            if ((leftDisplay == 'none') || (leftDisplay == ''))
              addf();   
            else  
              mergef();                             
          }
          else  
            addf();                                           
        }
        else  
          mergef();             
      }  
    }  
  });   
}; 

tools.ChartFlash = function (url, data, name, w, h) {
    var rtn = '<object classid="clsid:D27CDB6E-AE6D-11CF-96B8-444553540000" id="obj1" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8.0.42.0" border="0" width="' + w + '" height="' + h + '">';
    rtn = rtn + '<param name="movie" value="' + url + '">';
    rtn = rtn + '<param name="quality" value="high"> ';
    rtn = rtn + '<param name="FlashVars" value="&dataXML=' + data + '"> ';
    rtn = rtn + '<param name="wmode" value="opaque" /> ';
    rtn = rtn + '<embed wmode="opaque" src="' + url + '"  FlashVars="&dataXML=' + data + '" name = "' + name + '" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" name="obj1" width="' + w + '" height="' + h + '" quality="High" >';
    rtn = rtn + '</embed></object>';

    return rtn;
};

tools.ChartColor = ["AFD8F8", "F6BD0F", "8BBA00", "A66EDD", "F984A1", "1A77DF", "088322", "7D6006", "BD3201", "34A9FA", "703B7B", "13DA91"];

tools.GetPrintMoney = function (n) {
  n = n.toString();
  n='123.12';
  if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
    return "金额非法";

  var unit = "千百拾亿千百拾万千百拾元角分", str = "";
  
  n += "00";
  var p = n.indexOf('.');
  if (p >= 0) n = n.substring(0, p) + n.substr(p + 1, 2);
  
  unit = unit.substr(unit.length - n.length);
  
  for (var i=0; i < n.length; i++)
    str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    
  return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
};


tools.SetNullSelect = function (id, value) {
  if (Ext.isEmpty(value))
    tools.SetValue(id, gpersist.SELECT_NULL_VALUE);
  else
    tools.SetValue(id, value);
};


tools.GetQuarterStore = function(type) {
  var quarters = [];

  if (type == gpersist.SELECT_ALL_VALUE) {
    quarters.push([gpersist.SELECT_ALL_VALUE, '--所有季度--']);
  }

  quarters.push([1, '一季度']);
  quarters.push([2, '二季度']);
  quarters.push([3, '三季度']);
  quarters.push([4, '四季度']);

  return new Ext.data.ArrayStore({
    fields : ['id', 'name'],
    data : quarters
  });
}; 

tools.MustTitle = function (title) {
  return '<font color="Red">' + title + '</font>';
};

tools.log = function (msg, date) {
  if (typeof console == 'object') console.log(msg + '--' + (new Date() - date));
};

tools.GetPopupWindow = function (namespace, cname, config) {
  var classname = namespace + '.' + cname;
  var win;
  if (eval(classname)) {
    win = Ext.create(classname, config);
  }
  else {
    Ext.Ajax.request({
      url: 'jslib/views/popup/' + cname + '.js',
      async: false,
      method : 'GET',
      success: function (response, opts) {
        eval(response.responseText);          
      },
      failure: function (response) { }
    });
    
    win = Ext.create(classname, config);
  }
  
  return win;
};

tools.GetFormByPath = function (namespace, path, cname, config) {
  var classname = namespace + '.' + cname;
  var form;
  
  if (eval(classname)) {
    form = Ext.create(classname, config);
  }
  else {
    Ext.Ajax.request({
      url: 'jslib/' + path + cname + '.js',
      async: false,
      method : 'GET',
      success: function (response, opts) {
        eval(response.responseText);          
      },
      failure: function (response) { }
    });
    
    form = Ext.create(classname, config);
  }
  
  return form;
};

tools.JsonGetPost = function (url, params) {
  var data = [];

  params = params || {};

  Ext.Ajax.request({
    url: url,
    params: params,
    async: false,
    method: 'POST',
    success: function (response, opts) {
      //alert(response.responseText);
      try {
        data = Ext.decode(response.responseText.replace(/\r\n/g,'<br/>').replace(/\\/g,'').replace(/\"(new Date\((-*)\d+\))\"/g,'$1'));
      }
      catch(ex) {
        alert("JsonGet:" + ex.toString());
      }
      
    },
    failure: function (response) { }
  });

  return data;
};

tools.FormTreeCombo = function (label, url, name, id, anchor, blank, tab, labelwidth) { 
  var lw = 80;

  if (labelwidth) lw = labelwidth;
  
  var tb = Ext.create('Ext.ux.TreeCombo', {
    fieldLabel: label, labelWidth:lw, anchor:anchor, tabIndex: tab,
    name:name, id:id, allowBlank: blank, rootVisible: false, blankText: '请选择' + label + '！',
    store: Ext.create('Ext.data.TreeStore', {
      model:'gpersist.treemodel',
      proxy: {
          type: 'ajax',
          url: tools.GetUrl(url)
      }
    }), selectChildren: false, canSelectFolders: true, validateOnChange: false 
  });
  
  return tb;
};

tools.ToolBarTreeCombo = function (label, url, name, id, labelwidth, width) { 
  var lw = 80;

  if (labelwidth) lw = labelwidth;
  
  var tb = Ext.create('Ext.ux.TreeCombo', {
    fieldLabel: label, labelWidth:labelwidth, width:width,
    name:name, id:id, allowBlank: true, rootVisible: false, 
    store: Ext.create('Ext.data.TreeStore', {
      model:'gpersist.treemodel',
      proxy: {
          type: 'ajax',
          url: tools.GetUrl(url)
      }
    }), selectChildren: false, canSelectFolders: true, selectOnlyOne: true 
  });
  
  return tb;
};

tools.GetTreeComboValue = function (id) {
  var cb = Ext.getCmp(id);
  
  if (cb && cb.inputEl && cb.inputEl.getValue) {
    var text = cb.inputEl.getValue();
    
    if (Ext.isEmpty(text)) return '';
  }
  
  return cb.getValue();
};

tools.AutoFormTextFlex = function (label, name, id, max, blank, tab, vtype, labelwidth, flex, anchor) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var ab = Ext.create('Ext.form.TextField', {
    fieldLabel: label, name: name, id: id,
    maxLength: max, maxLengthText: label + '长度不能超过' + max + '个字符！', selectOnFocus: false, labelWidth: lw,
    blankText: label + '不能为空！', allowBlank: blank, flex: flex, vtype: vtype, tabIndex: tab, labelPad: 5, labelAlign: 'right',
    anchor:anchor
  });
  
  return ab;
};

tools.AutoFormTextArea = function (label, name, id, max, anchor, blank, tab, rows, labelwidth) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var ab = Ext.create('Ext.form.TextArea', {
    fieldLabel: label, name: name, id: id,
    maxLength: max, maxLengthText: label + '长度不能超过' + max + '个字符！', selectOnFocus: false, labelWidth: lw,
    blankText: label + '不能为空！', allowBlank: blank, anchor: '100%', tabIndex: tab, rows: 3
  });
  
  return ab;
};

tools.AutoFormComboFlex = function (label, name, id, store, blank, tab, labelwidth, isinit, flex) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;
//不可以手写editable: false,
  var cb = Ext.create('Ext.form.ComboBox', {
    fieldLabel: label, name: name, id: id,
    mode: 'local', triggerAction: 'all', forceSelection: true, selectOnFocus: false, 
    editable: false, queryMode: 'local',labelWidth: lw,
    valueField: 'id', displayField: 'name', allowBlank: blank, blankText: '请选择' + label + '！', store: store,
    flex: flex, tabIndex: tab, labelPad: 5, labelAlign: 'right'
  });

  if (isinit) return cb;

  if (cb.store.data.length > 0)
    cb.setValue(cb.store.getAt(0).data.id);
  
  return cb;
};

tools.AutoFormCheckComboFlex = function (label, name, id, store, blank, tab, labelwidth, isinit, flex) {
  var lw = 80;

  if (labelwidth) lw = labelwidth;

  var cb = Ext.create('Ext.ux.CheckCombo', {
    fieldLabel: label, name: name, id: id,
    mode: 'local', triggerAction: 'all', forceSelection: true, selectOnFocus: false, 
    editable: false, queryMode: 'local',labelWidth: lw,
    valueField: 'id', displayField: 'name', allowBlank: blank, blankText: '请选择' + label + '！', store: store,
    flex: flex, tabIndex: tab, labelPad: 5, labelAlign: 'right'
  });

  if (isinit) return cb;

  if (cb.store.data.length > 0)
    cb.setValue(cb.store.getAt(0).data.id);
  
  return cb;
};

tools.GetTypeList = function (type) {
  var typedata = [];
  
  if (type) {
    if (type == gpersist.SELECT_ALL_VALUE) {
      typedata.push({ id: gpersist.SELECT_ALL_VALUE, name: '--选择全部--' });
    }
    else if (type == gpersist.SELECT_NULL_VALUE) {
      typedata.push({ id: gpersist.SELECT_NULL_VALUE, name: '--无选择--' });
    }
    else if (type == gpersist.SELECT_MUST_VALUE) {
      typedata.push({ id: gpersist.SELECT_MUST_VALUE, name: '--请选择--' });
    }
  }
  
  return typedata;
};

tools.SetLabel = function (id, label) {
  var item = Ext.getCmp(id);
  
  if (item && item.setFieldLabel) {
    item.setFieldLabel(label);
  }
};

tools.SwfUpload = function(uploadlimit){
  var ul = 50;
  if(uploadlimit){
    ul = uploadlimit;
  }
  var swfupload = Ext.create('Ext.ux.uploadPanel.UploadPanel',{file_upload_limit:ul});
  return swfupload;
};

tools.DoAction = function (url, params) {
  var data = '';

  params = params || {};
  Ext.Ajax.request({
    url: url,
    params: params,
    async: false,
    success: function (response, opts) {
      data = response.responseText;
    },
    failure: function (response) { }
  });
  return data
};

tools.UserPicker = function (id,name,label,lw,ac,tab){
  var labelWidth = 80;
  if(lw){
    labelWidth = lw;
  }
  var anchor = '96%';
  if(ac){
    anchor = ac;
  }
  
  var up = Ext.create('Ext.ux.UserPicker', {
    fieldLabel: label, name: name, id:id, winTitle: '系统员工',
    selectOnFocus: false, labelWidth: labelWidth,
    allowBlank: true, anchor:anchor, tabIndex: tab, editable: false,
    columnUrl: 'SysSqlColumn.do?sqlid=p_get_user',
    storeUrl: 'BasSearchUser.do',
    hasPage: true, pickerWidth: 500, pickerHeight: 450
  });
  return up;
};

tools.LabSample = function(nowyear,monthinfo,labyearone,labyeartwo,labyearthree,labyearfour,labyearfive){
    
  var labsample = Ext.create('Ext.ux.LSChartsComponent',{nowyear:nowyear,monthinfo:monthinfo,labyearone:labyearone,labyeartwo:labyeartwo,
     labyearthree:labyearthree,labyearfour:labyearfour,labyearfive:labyearfive});
     
  return labsample;
};

tools.ServerInfo = function(nowyear,testusernames,paramcounts){
  var needtotal = Ext.create('Ext.ux.SOChartsComponent',{nowyear:nowyear,testusernames:testusernames,paramcounts:paramcounts});
  return needtotal;
};

tools.PrdsType = function(titledata,prdsdata){
  var prdstype = Ext.create('Ext.ux.PChartsComponent',{titledata:titledata,prdsdata:prdsdata});
  return prdstype;
};

tools.PrdsCodeCount = function(prdnames,lastnumbers){
  var prdstype = Ext.create('Ext.ux.PCChartsComponent',{prdnames:prdnames,lastnumbers:lastnumbers});
  return prdstype;
};

tools.PrdOutPut = function(nowyear,monthinfo,prdinsyear,prdoutsyear){
  var prdstype = Ext.create('Ext.ux.POPChartsComponent',{nowyear:nowyear,monthinfo:monthinfo,prdinsyear:prdinsyear,prdoutsyear:prdoutsyear});
  return prdstype;
};

tools.SamplePass = function(kindnames,sampass,samnopass){
  var samplepass = Ext.create('Ext.ux.SPChartsComponent',{kindnames:kindnames,sampass:sampass,samnopass:samnopass});
  return samplepass;
};

tools.DevsType = function(titledata,devsdata){
  var devstype = Ext.create('Ext.ux.DTChartsComponent',{titledata:titledata,devsdata:devsdata});
  return devstype;
};

tools.TesterParameter = function(titledata,testerdata,paratotals){
  var testersparameter = Ext.create('Ext.ux.TPChartsComponent',{titledata:titledata,testerdata:testerdata,paratotals:paratotals});
  return testersparameter;
};

tools.TesterSample = function(titledata,testerdata,samtotals){
  var testersample = Ext.create('Ext.ux.TSChartsComponent',{titledata:titledata,testerdata:testerdata,samtotals:samtotals});
  return testersample;
};

tools.DevUseCount = function(nowyear,devnames,devusecount){
  var devuse = Ext.create('Ext.ux.DUCChartsComponent',{nowyear:nowyear,devnames:devnames,devusecount:devusecount});
  return devuse;
};

tools.CusOrder = function(nowyear,maxcount,serverdata,provincenames,procounts){
  var devuse = Ext.create('Ext.ux.COChartsComponent',{nowyear:nowyear,maxcount:maxcount, serverdata:serverdata,provincenames:provincenames,procounts:procounts});
  return devuse;
};

tools.SendSample = function(nowyear,monthinfo,sendyearone,sendyeartwo,sendyearthree,sendyearfour,sendyearfive,
  sendyearsix,sendyearseven,sendyeareight,sendyearnine,sendyearten){
    
  var sendsample = Ext.create('Ext.ux.SSChartsComponent',{nowyear:nowyear,monthinfo:monthinfo,sendyearone:sendyearone,sendyeartwo:sendyeartwo,
     sendyearthree:sendyearthree,sendyearfour:sendyearfour,sendyearfive:sendyearfive,sendyearsix:sendyearsix,sendyearseven:sendyearseven,
     sendyeareight:sendyeareight,sendyearnine:sendyearnine,sendyearten:sendyearten});
     
  return sendsample;
};

tools.CustomerCount = function(nowyear,testednames,counts){
  var customercount = Ext.create('Ext.ux.CCChartsComponent',{nowyear:nowyear,testednames:testednames,counts:counts});
  return customercount;
};
