import tensorflow as tf


class ModelTest(tf.test.TestCase):
    def test_model(self):
        model_name = 'optimized_logic_and_gate.pb'
        with tf.Graph().as_default():
            with tf.gfile.FastGFile('models/{}'.format(model_name), 'rb') as file:
                graph_def = tf.GraphDef()
                graph_def.ParseFromString(file.read())
                tf.import_graph_def(graph_def, name='')

            operations = tf.get_default_graph().get_operations()
            self.assertEquals(operations[0].name, 'x')
            self.assertEquals(operations[len(operations) - 1].name, 'y_pred')

            with self.test_session() as session:
                y_pred = session.run('y_pred:0', feed_dict={
                    'x:0': [[1, 1]]
                })
                self.assertGreater(y_pred, 0.8)

    def test_square(self):
        with self.test_session():
            x = tf.square([2, 3])
            self.assertAllEqual(x.eval(), [4, 9])
