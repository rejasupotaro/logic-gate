
;
xPlaceholder*
dtype0*
shape:���������
F
weight_quint8_constConst*
valueB"��*
dtype0
7

weight_minConst*
valueB
 *    *
dtype0
7

weight_maxConst*
valueB
 *���@*
dtype0
S
MatMul_eightbit_reshape_dimsConst*
valueB:
���������*
dtype0
L
MatMul_eightbit_reduction_dimsConst*
valueB: *
dtype0
N
MatMul_eightbit_reshape_xReshapexMatMul_eightbit_reshape_dims*
T0
q
MatMul_eightbit_min_xMinMatMul_eightbit_reshape_xMatMul_eightbit_reduction_dims*
	keep_dims( *
T0
q
MatMul_eightbit_max_xMaxMatMul_eightbit_reshape_xMatMul_eightbit_reduction_dims*
T0*
	keep_dims( 
w
MatMul_eightbit_quantize_x
QuantizeV2xMatMul_eightbit_min_xMatMul_eightbit_max_x*
T0*
mode	MIN_FIRST
�
!MatMul_eightbit_quantized_mat_mulQuantizedMatMulMatMul_eightbit_quantize_xweight_quint8_constMatMul_eightbit_quantize_x:1MatMul_eightbit_quantize_x:2
weight_min
weight_max*
Toutput0*
T10*
T20*
transpose_a( *
transpose_b( 
�
MatMul_eightbit_requant_rangeRequantizationRange!MatMul_eightbit_quantized_mat_mul#MatMul_eightbit_quantized_mat_mul:1#MatMul_eightbit_quantized_mat_mul:2*
Tinput0
�
MatMul_eightbit_requantize
Requantize!MatMul_eightbit_quantized_mat_mul#MatMul_eightbit_quantized_mat_mul:1#MatMul_eightbit_quantized_mat_mul:2MatMul_eightbit_requant_range:0MatMul_eightbit_requant_range:1*
Tinput0*
out_type0
�
MatMul
DequantizeMatMul_eightbit_requantizeMatMul_eightbit_requantize:1MatMul_eightbit_requantize:2*
T0*
mode	MIN_FIRST
5
biasConst*
valueB*+`2�*
dtype0
!
addAddMatMulbias*
T0

y_predSigmoidadd*
T0